package com.ifce.jedi.service;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.ifce.jedi.dto.User.RegisterDto;
import com.ifce.jedi.dto.User.UpdateUserDto;
import com.ifce.jedi.dto.User.UserResponseDto;
import com.ifce.jedi.dto.User.UserTableResponseDto;
import com.ifce.jedi.exception.custom.EmailAlreadyUsedException;
import com.ifce.jedi.exception.custom.UploadException;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.model.User.StatusPreInscricao;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.PreInscricaoRepository;
import com.ifce.jedi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreInscricaoRepository preInscricaoRepository;

    @Autowired
    private LocalStorageService localStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public void register(RegisterDto dto) throws IOException {
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(dto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Função inválida: " + dto.getRole());
        }

        if (userRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new EmailAlreadyUsedException("Usuário já existe com esse e-mail.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        String photoUrl = null;

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            String uploadResult = localStorageService.salvar(dto.getPhoto());
            photoUrl = baseUrl + "/publicos/" + uploadResult.replaceAll("\\s+", "_");
        }

        User newUser = new User(
                dto.getName(),
                dto.getLogin(),
                userRole,
                encryptedPassword,
                photoUrl
        );

        userRepository.save(newUser);
    }

    public Page<PreInscricaoDadosDto> getAllPreInscricoes(
            String nome,
            String email,
            StatusPreInscricao status,
            Pageable pageable
    ) {
        Specification<PreInscricao> spec = (root, query, builder) -> builder.conjunction();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("completeName")), nome.toLowerCase() + "%"));
        }

        if (email != null && !email.isBlank()) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        if (status != null) {
            if (status == StatusPreInscricao.TODOS) {
                spec = spec.and((root, query, builder) ->
                        builder.or(
                                builder.equal(root.get("status"), StatusPreInscricao.COMPLETO),
                                builder.equal(root.get("status"), StatusPreInscricao.INCOMPLETO),
                                builder.isNull(root.get("status"))
                        ));
            } else {
                spec = spec.and((root, query, builder) ->
                        builder.equal(root.get("status"), status));
            }
        }

        return preInscricaoRepository.findAll(spec, pageable)
                .map(PreInscricaoDadosDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserTableResponseDto> getPaginatedUsers(Pageable pageable, String searchTerm) {
        List<User> filteredUsers = userRepository.findAll().stream()
                .filter(user -> searchTerm == null || searchTerm.isBlank() ||
                        user.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        user.getLogin().toLowerCase().contains(searchTerm.toLowerCase()))
                .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                .toList();

        int totalItems = filteredUsers.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<User> pageUsers = startItem >= totalItems ? Collections.emptyList() :
                filteredUsers.subList(startItem, Math.min(startItem + pageSize, totalItems));

        List<UserTableResponseDto> dtos = pageUsers.stream()
                .map(UserTableResponseDto::new)
                .toList();

        return new PageImpl<>(dtos, pageable, totalItems);
    }

    @Transactional
    public UserResponseDto updateUser(UUID userId, UpdateUserDto dto) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getLogin() != null && !dto.getLogin().isBlank()) {
            if (userRepository.findByLogin(dto.getLogin()).isPresent() && !user.getLogin().equals(dto.getLogin())) {
                throw new EmailAlreadyUsedException("Email já está em uso por outro usuário.");
            }
            user.setLogin(dto.getLogin());
        }

        if (dto.getRole() != null && !dto.getRole().isBlank()) {
            try {
                UserRole userRole = UserRole.valueOf(dto.getRole().toUpperCase());
                user.setRole(userRole);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Função inválida: " + dto.getRole());
            }
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (dto.getConfirmPassword() == null || !dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new IllegalArgumentException("Senha e confirmação não coincidem.");
            }
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
            user.setPassword(encryptedPassword);
        }

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            if (user.getPhotoUrl() != null) {
                String oldFileName = user.getPhotoUrl().replace(baseUrl + "/publicos/", "").replace("_", " ");
                localStorageService.deletar(oldFileName);
            }
            String uploadResult = localStorageService.salvar(dto.getPhoto());
            user.setPhotoUrl(baseUrl + "/publicos/" + uploadResult.replaceAll("\\s+", "_"));
        }

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(UUID userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        if (user.getPhotoUrl() != null) {
            String fileName = user.getPhotoUrl().replace(baseUrl + "/publicos/", "").replace("_", " ");
            localStorageService.deletar(fileName);
        }

        userRepository.delete(user);
    }
}