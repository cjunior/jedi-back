package com.ifce.jedi.service;


import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.ifce.jedi.dto.User.RegisterDto;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.model.User.StatusPreInscricao;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.PreInscricaoRepository;
import com.ifce.jedi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PreInscricaoRepository preInscricaoRepository;

    public void register(RegisterDto dto) {
        if (userRepository.findByLogin(dto.login()) != null) {
            throw new IllegalArgumentException("Usuário já existe com esse e-mail.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User(dto.name(), dto.login(), UserRole.GERENTE, encryptedPassword);

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
