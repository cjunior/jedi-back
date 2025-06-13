package com.ifce.jedi.config;

import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.model.UserRole;
import com.ifce.jedi.model.User;
import com.ifce.jedi.repository.UserRepository;
import com.ifce.jedi.service.HeaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class StartupInitializer {

    @Bean
    public CommandLineRunner initDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, HeaderService headerService) {
        return args -> {
            String email = "admin@example.com";

            if (userRepository.findByLogin(email) == null) {
                String passwordHash = passwordEncoder.encode("!Admin123");

                User admin = new User(email, passwordHash, UserRole.ADMIN);
                userRepository.save(admin);

                System.out.println("Usuário admin criado com sucesso.");
            } else {
                System.out.println("Usuário admin já existe.");
            }

            if (headerService.getHeader() == null) {
                HeaderDto headerDto = new HeaderDto(
                        "https://exemplo.com/logo.png", //preciso mudar depois para a url do bucket
                        "O Projeto",
                        "Conteúdo",
                        "Ajuda",
                        "#RedeJED",
                        "Entrar"
                );
                headerService.createHeader(headerDto);
                System.out.println("Header padrão criado com sucesso.");
            } else {
                System.out.println("Header já existe.");
            }
        };
    }


}
