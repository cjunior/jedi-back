package com.ifce.jedi.config;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemUrlDto;
import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.Header.HeaderUrlDto;
import com.ifce.jedi.dto.Team.TeamDto;
import com.ifce.jedi.dto.Team.TeamItemUrlDto;
import com.ifce.jedi.model.UserRole;
import com.ifce.jedi.model.User;
import com.ifce.jedi.repository.UserRepository;
import com.ifce.jedi.service.BannerService;
import com.ifce.jedi.service.CloudinaryService;
import com.ifce.jedi.service.HeaderService;
import com.ifce.jedi.service.TeamService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class StartupInitializer {

    @Bean
    public CommandLineRunner initDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
        };
    }

    @Bean
    public CommandLineRunner initDefaultHeader(CloudinaryService cloudinaryService,HeaderService headerService) {
        return args -> {
            if (headerService.getHeader() == null) {
                var logoUrl = "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png";
                String publicId = "d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6";

                HeaderUrlDto headerUrlDto = new HeaderUrlDto(
                        logoUrl,
                        publicId,
                        "O Projeto",
                        "Conteúdo",
                        "Ajuda",
                        "#RedeJED",
                        "Entrar"
                );
                headerService.createHeader(headerUrlDto);
                System.out.println("Header padrão criado com sucesso.");
            } else {
                System.out.println("Header já existe.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultBanner(BannerService bannerService){
        return args -> {
            if (bannerService.getBanner() == null) {
                BannerDto bannerDto = new BannerDto(
                        "DÊ UM PLAY NO SEU FUTURO",
                        "Curso online com formação personalizada para você empreender de forma inteligente e estratégica",
                        List.of(new BannerItemUrlDto(
                                "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                "ASSISTA AO MANIFESTO",
                                ""
                        ))
                );
                bannerService.createBanner(bannerDto);
                System.out.println("Banner padrão criado com sucesso.");
            } else {
                System.out.println("Banner já existe.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultBannerTeam(TeamService teamService){
        return args -> {
            if (teamService.getTeam() == null) {
                TeamDto teamDto = new TeamDto(
                        "Equipe",
                        List.of(new TeamItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg"),
                                new TeamItemUrlDto("https://res.cloudinary.com/dp98r2imm/image/upload/v1749945082/fotoend2_ajfnzd.png"))
                );
                teamService.createTeam(teamDto);
                System.out.println("BannerEquipe padrão criado com sucesso.");
            } else {
                System.out.println("BannerEquipe já existe.");
            }
        };
    }
}
