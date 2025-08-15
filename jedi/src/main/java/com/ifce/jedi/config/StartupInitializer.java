package com.ifce.jedi.config;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemUrlDto;
import com.ifce.jedi.dto.ContactUs.ContactUsDto;
import com.ifce.jedi.dto.Contents.ContentDto;
import com.ifce.jedi.dto.Contents.ContentItemUrlDto;
import com.ifce.jedi.dto.Header.HeaderUrlDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionDto;
import com.ifce.jedi.dto.Team.TeamDto;
import com.ifce.jedi.dto.Team.TeamItemUrlDto;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediImage;
import com.ifce.jedi.model.SecoesSite.Rede.RedeJediSection;
import com.ifce.jedi.model.User.User;
import com.ifce.jedi.model.User.UserRole;
import com.ifce.jedi.repository.RedeJediImageRepository;
import com.ifce.jedi.repository.RedeJediSectionRepository;
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
    public CommandLineRunner initDefaultHeader(HeaderService headerService) {
        return args -> {
            if (headerService.getHeader() == null) {
                HeaderUrlDto headerUrlDto = new HeaderUrlDto(
                        "",      // url vazia
                        "",      // publicId vazio
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
    public CommandLineRunner initDefaultBanner(BannerService bannerService) {
        return args -> {
            if (bannerService.getBanner() == null) {
                BannerDto bannerDto = new BannerDto(
                        "DÊ UM PLAY NO SEU FUTURO",
                        "Curso online com formação personalizada para você empreender de forma inteligente e estratégica",
                        List.of(
                                new BannerItemUrlDto(
                                        "",           // url vazia
                                        "Saiba mais",
                                        ""            // link opcional vazio
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
    public CommandLineRunner initDefaultTeam(TeamService teamService) {
        return args -> {
            if (teamService.getTeam() == null) {
                TeamDto teamDto = new TeamDto(
                        "Equipe",
                        List.of(
                                new TeamItemUrlDto(""),  // url vazia
                                new TeamItemUrlDto("")   // url vazia
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
                        "O PROJETO",
                        "Lorem ipsum dolor sit amet...",
                        "Duis fermentum velit at sapien...",
                        "150 DE ESTUDANTES",
                        "5000 DE ALCANCE",
                        "",    // url vazia
                        "Imagem ilustrativa do projeto"
                );
                service.create(dto);
                System.out.println("Presentation Section criada.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultContentSection(ContentService contentService) {
        return args -> {
            if (contentService.getContent() == null) {
                ContentDto dto = new ContentDto(
                        "CONTEÚDOS",
                        "Lorem ipsum",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ac ullamcorper metus.",
                        "",    // url vazia
                        "PERCURSO BÁSICO",
                        List.of(
                                new ContentItemUrlDto("", ""),                 // url vazia
                                new ContentItemUrlDto("", "VENDAS NO WHATSAPP"),
                                new ContentItemUrlDto("", "")                  // url vazia
                        )
                );
                contentService.createContent(dto);
                System.out.println("Content Section criada.");
            } else {
                System.out.println("Content Section já existe.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultContactSection(ContactUsService contactUsService) {
        return args -> {
            if (contactUsService.getSection() == null) {
                ContactUsDto dto = new ContactUsDto(
                        "Fale Conosco",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis consequat lobortis dui vitae laoreet.",
                        "Preencha o formulário ao lado para entrar em contato."
                );
                contactUsService.createSection(dto);
                System.out.println("ContactUs Section criada.");
            } else {
                System.out.println("ContactUs Section já existe.");
            }
        };
    }

    @Bean
    public CommandLineRunner initRedeJediSectionAndImages(
            RedeJediSectionRepository sectionRepo,
            RedeJediImageRepository imageRepo
    ) {
        return args -> {
            RedeJediSection section = sectionRepo.findById(1L).orElse(null);
            if (section == null) {
                section = new RedeJediSection();
                section.setTitulo("Título padrão Rede Jedi");
                section = sectionRepo.save(section);
                System.out.println("Seção Rede Jedi criada.");
            }

            if (imageRepo.count() == 0) {
                List<RedeJediImage> imagens = List.of(
                        new RedeJediImage(
                                "", // url vazia
                                ""  // publicId vazio
                        )
                );

                RedeJediSection finalSection = section;
                imagens.forEach(img -> img.setSection(finalSection));
                imageRepo.saveAll(imagens);
                System.out.println("Imagens iniciais associadas à seção.");
            }
        };
    }

    @Bean
    public CommandLineRunner initDefaultBlogSection(BlogSectionService service) {
        return args -> {
            // Já está implementado no próprio service a criação dos dados padrão
            service.get(); // Irá criar a seção com itens padrão se não existir
        };
    }
}
