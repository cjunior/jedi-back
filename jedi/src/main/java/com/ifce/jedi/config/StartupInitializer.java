package com.ifce.jedi.config;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemUrlDto;
import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionDto;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.UserRepository;
import com.ifce.jedi.service.BannerService;
import com.ifce.jedi.service.HeaderService;
import com.ifce.jedi.service.PresentationSectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class StartupInitializer {

    @Bean
    public CommandLineRunner initDefaultData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            HeaderService headerService,
            BannerService bannerService,
            PresentationSectionService presentationSectionService
    ) {
        return args -> {
            // 🛡️ Criação do usuário admin padrão
            String email = "admin@example.com";

            if (userRepository.findByLogin(email) == null) {
                String passwordHash = passwordEncoder.encode("!Admin123");

                User admin = new User(email, passwordHash, UserRole.ADMIN);
                userRepository.save(admin);

                System.out.println("✅ Usuário admin criado com sucesso.");
            } else {
                System.out.println("ℹ️ Usuário admin já existe.");
            }

            // 🌐 Criação do Header padrão
            if (headerService.getHeader() == null) {
                HeaderDto headerDto = new HeaderDto(
                        "https://exemplo.com/logo.png", // <- Atualizar depois para a URL real
                        "O Projeto",
                        "Conteúdo",
                        "Ajuda",
                        "#RedeJED",
                        "Entrar"
                );
                headerService.createHeader(headerDto);
                System.out.println("✅ Header padrão criado.");
            } else {
                System.out.println("ℹ️ Header já existe.");
            }

            // 🏞️ Criação do Banner padrão
            if (bannerService.getBanner() == null) {
                BannerDto bannerDto = new BannerDto(
                        "Bem-vindo ao Projeto Jedi!",
                        "Transformando o futuro da educação pública.",
                        List.of(
                                new BannerItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                        "Saiba mais",
                                        "" // Link opcional
                                )
                        )
                );
                bannerService.createBanner(bannerDto);
                System.out.println("✅ Banner padrão criado.");
            } else {
                System.out.println("ℹ️ Banner já existe.");
            }

            // 📄 Criação da Presentation Section padrão
            if (presentationSectionService.getPresentationSection() == null) {
                PresentationSectionDto dto = new PresentationSectionDto(
                        "Projeto de integração de estudantes com impacto social.",
                        "150 ESTUDANTES ATIVOS",
                        "5000 PESSOAS IMPACTADAS",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                        "Nosso projeto visa promover integração, desenvolvimento pessoal e impacto social."
                );
                presentationSectionService.createPresentationSection(dto);
                System.out.println("✅ Presentation Section criada.");
            } else {
                System.out.println("ℹ️ Presentation Section já existe.");
            }
        };
    }
}
