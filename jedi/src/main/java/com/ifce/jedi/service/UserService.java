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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PreInscricaoRepository preInscricaoRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public void register(RegisterDto dto) {
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(dto.getRole().toUpperCase()); // Aceita string minúscula também
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Função inválida: " + dto.getRole());
        }

        if (userRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new EmailAlreadyUsedException("Usuário já existe com esse e-mail.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        String photoUrl = null;

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            try {
                Map<String, String> uploadResult = cloudinaryService.uploadImage(dto.getPhoto());
                photoUrl = uploadResult.get("url");
            } catch (IOException e) {
                throw new UploadException("Erro ao fazer upload da foto no Cloudinary", e);
            }
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
        // Filtra usuários (melhorar com query JPQL para grandes volumes)
        List<User> filteredUsers = userRepository.findAll().stream()
                .filter(user -> searchTerm == null || searchTerm.isBlank() ||
                        user.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        user.getLogin().toLowerCase().contains(searchTerm.toLowerCase()))
                .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                .toList();

        // Paginação manual (igual ao seu exemplo)
        int totalItems = filteredUsers.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<User> pageUsers = startItem >= totalItems ? Collections.emptyList() :
                filteredUsers.subList(startItem, Math.min(startItem + pageSize, totalItems));

        // Converte para DTO
        List<UserTableResponseDto> dtos = pageUsers.stream()
                .map(UserTableResponseDto::new)
                .toList();

        return new PageImpl<>(dtos, pageable, totalItems);
    }
    @Transactional
    public UserResponseDto updateUser(UUID userId, UpdateUserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        // Atualiza nome (se fornecido)
        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        // Atualiza email/login (se fornecido e não repetido)
        if (dto.getLogin() != null && !dto.getLogin().isBlank()) {
            if (userRepository.findByLogin(dto.getLogin()).isPresent() && !user.getLogin().equals(dto.getLogin())) {
                throw new EmailAlreadyUsedException("Email já está em uso por outro usuário.");
            }
            user.setLogin(dto.getLogin());
        }

        // Atualiza role (se fornecido e válida)
        if (dto.getRole() != null && !dto.getRole().isBlank()) {
            try {
                UserRole userRole = UserRole.valueOf(dto.getRole().toUpperCase());
                user.setRole(userRole);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Função inválida: " + dto.getRole());
            }
        }

        // Atualiza senha (se fornecida e válida)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (dto.getConfirmPassword() == null || !dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new IllegalArgumentException("Senha e confirmação não coincidem.");
            }
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
            user.setPassword(encryptedPassword);
        }

        // Atualiza foto (se fornecida)
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            try {
                // Remove foto antiga do Cloudinary (se existir)
                if (user.getPhotoUrl() != null) {
                    cloudinaryService.deleteImage(user.getPhotoUrl());
                }
                // Upload da nova foto
                Map<String, String> uploadResult = cloudinaryService.uploadImage(dto.getPhoto());
                user.setPhotoUrl(uploadResult.get("url"));
            } catch (IOException e) {
                throw new UploadException("Erro ao atualizar a foto", e);
            }
        }

        userRepository.save(user);
        return new UserResponseDto(user);
    }
}
