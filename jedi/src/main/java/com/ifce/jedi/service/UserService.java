package com.ifce.jedi.service;


import com.ifce.jedi.dto.User.RegisterDto;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void register(RegisterDto dto) {
        if (userRepository.findByLogin(dto.login()) != null) {
            throw new IllegalArgumentException("Usuário já existe com esse e-mail.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User(dto.name(), dto.login(), UserRole.GERENTE, dto.password());

        userRepository.save(newUser);
    }
}
