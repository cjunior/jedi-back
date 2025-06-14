package com.ifce.jedi.config;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemUrlDto;
import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.model.UserRole;
import com.ifce.jedi.model.User;
import com.ifce.jedi.repository.UserRepository;
import com.ifce.jedi.service.BannerService;
import com.ifce.jedi.service.HeaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class StartupInitializer {

    @Bean
    public CommandLineRunner initDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, HeaderService headerService, BannerService bannerService) {
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
            if (bannerService.getBanner() == null) {
                BannerDto bannerDto = new BannerDto(
                        "Bem-vindo ao Projeto Jedi!",
                        "Transformando o futuro da educação pública.",
                        List.of(new BannerItemUrlDto(
                                "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                "Saiba mais",
                                "" // a principio vou deixar vazio mas o cliente manda o que ele quiser que seja iniciado.
                        ))
                );
                bannerService.createBanner(bannerDto);
                System.out.println("Banner padrão criado com sucesso.");
            } else {
                System.out.println("Banner já existe.");
            }
        };
    }


}
