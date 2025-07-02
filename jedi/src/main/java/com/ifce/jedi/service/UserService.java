package com.ifce.jedi.service;


import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.ifce.jedi.dto.User.RegisterDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

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




}
