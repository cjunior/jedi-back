package com.ifce.jedi.service;


import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.ifce.jedi.dto.User.RegisterDto;
import com.ifce.jedi.model.User.PreInscricao;
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

    public Page<PreInscricaoDadosDto> getAllPreInscricoes(String nome, String email, Boolean somenteCompletos, Pageable pageable) {
        Specification<PreInscricao> spec = (root, query, builder) -> builder.conjunction();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("completeName")), nome.toLowerCase() + "%"));
        }

        if (email != null && !email.isBlank()) {
            spec = spec.and((root, query, builder) ->
                    builder.like(builder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        if (Boolean.TRUE.equals(somenteCompletos)) {
            spec = spec.and((root, query, builder) -> builder.and(
                    builder.isNotNull(root.get("completeName")),
                    builder.isNotNull(root.get("email")),
                    builder.isNotNull(root.get("cellPhone")),
                    builder.isNotNull(root.get("birthDate")),
                    builder.isNotNull(root.get("municipality")),
                    builder.isNotNull(root.get("cpf")),
                    builder.isNotNull(root.get("rg")),
                    builder.isNotNull(root.get("document")),
                    builder.isNotNull(root.get("proofOfAdress"))
            ));
        }

        return preInscricaoRepository.findAll(spec, pageable)
                .map(PreInscricaoDadosDto::fromEntity); // <-- Aqui está a correção
    }





}
