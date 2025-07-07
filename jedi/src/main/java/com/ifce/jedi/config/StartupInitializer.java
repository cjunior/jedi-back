package com.ifce.jedi.config;

import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class StartupInitializer {

    @Bean
    public CommandLineRunner initDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@example.com";

            if (userRepository.findByLogin(email).isEmpty()) {
                String passwordHash = passwordEncoder.encode("!Admin123");

                User admin = new User(email, passwordHash, UserRole.ADMIN);
                userRepository.save(admin);

                System.out.println("Usuário admin criado com sucesso.");
            } else {
                System.out.println("Usuário admin já existe.");
            }
        };
    }
}