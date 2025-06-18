package com.ifce.jedi.config;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemUrlDto;
import com.ifce.jedi.dto.Contents.ContentDto;
import com.ifce.jedi.dto.Contents.ContentItemUrlDto;
import com.ifce.jedi.dto.Header.HeaderUrlDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionDto;
import com.ifce.jedi.dto.Team.TeamDto;
import com.ifce.jedi.dto.Team.TeamItemUrlDto;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.UserRepository;
import com.ifce.jedi.service.*;
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
    public CommandLineRunner initDefaultHeader(HeaderService headerService){
        return args -> {
            if (headerService.getHeader() == null) {
                HeaderUrlDto headerUrlDto = new HeaderUrlDto(
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png",
                        "d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6",
                        "O Projeto",
                        "Conteúdo",
                        "Ajuda",
                        "#RedeJED",
                        "Entrar"
                );
                headerService.createHeader(headerUrlDto);
                System.out.println("Header padrão criado.");
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
                        List.of(
                                new BannerItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                        "Saiba mais",
                                        "" // Link opcional
                                )
                        )
                );
                bannerService.createBanner(bannerDto);
                System.out.println("Banner padrão criado.");
            } else {
                System.out.println("Banner já existe.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultTeam(TeamService teamService){
        return args -> {
            if(teamService.getTeam() == null){
                TeamDto teamDto = new TeamDto(
                    "Equipe",
                        List.of(
                                    new TeamItemUrlDto(
                                            "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg"
                                    ),
                                new TeamItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749945082/fotoend2_ajfnzd.png"
                                )
                        )
                );
                teamService.createTeam(teamDto);
                System.out.println("Banner da Equipe padrão criado.");
            } else {
                System.out.println("Banner da Equipe já existe.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultPresentationSection(PresentationSectionService service) {
        return args -> {
            if (service.get() == null) {
                PresentationSectionDto dto = new PresentationSectionDto(
                        "0 PROJETO",
                        "Lorem ipsum dolor sit amet...",
                        "Duis fermentum velit at sapien...",
                        "150 DE ESTUDANTES",
                        "5000 DE ALCANCE",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749945082/fotoend2_ajfnzd.png",
                        "Imagem ilustrativa do projeto"
                );
                service.create(dto);
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultContentSection(ContentService contentService){
        return args -> {
            if(contentService.getContent() == null) {
                ContentDto dto = new ContentDto(
                        "CONTEÚDOS",
                        "Lorem ipsum",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ac ullamcorper metus.",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                        List.of(
                                new ContentItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                        ""
                                ),
                                new ContentItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                        "VENDAS NO WHATSAPP"
                                ),
                                new ContentItemUrlDto(
                                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749910911/bannerInicial_xcwltm.jpg",
                                        ""
                                )
                        )
                );
                contentService.createContent(dto);
                System.out.println("Content Section criada.");
            }else {
                System.out.println("Content Section já existe.");
            }
        };
    }
}