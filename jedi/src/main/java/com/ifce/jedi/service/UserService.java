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

    public List<PreInscricaoDadosDto> getAllPreInscricoes() {
        List<PreInscricao> preInscricoes = preInscricaoRepository.findAll();
        return preInscricoes.stream()
                .map(PreInscricaoDadosDto::fromEntity)
                .collect(Collectors.toList());
    }



}
