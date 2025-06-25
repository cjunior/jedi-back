package com.ifce.jedi.service;

import com.ifce.jedi.dto.Blog.*;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogItem;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogSection;
import com.ifce.jedi.repository.BlogSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BlogSectionService {

    @Autowired
    private BlogSectionRepository repository;

    @Autowired
    private LocalStorageService localStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public BlogSectionResponseDto get() {
        Optional<BlogSection> entity = repository.findFirstByOrderByIdAsc();
        return entity.map(this::toResponse).orElseGet(this::createDefaultSectionResponse);
    }

    public BlogItemResponseDto getBlogItemById(Long itemId) {
        BlogItem item = repository.findBlogItemById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com o ID: " + itemId));

        return new BlogItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getAuthor(),
                item.getDate(),
                item.getReadingTime(),
                item.getImageUrl(),
                item.getImageDescription(),
                item.getIconUrl(),      // Novo campo
                item.getDescription()   // Novo campo
        );
    }

    private BlogSection createDefaultSection() {
        BlogSection section = new BlogSection();
        section.setTitle("Últimas postagens do blog");

        // URL base para as imagens (reutilizada para iconUrl também)
        String defaultImageUrl = "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png";

        List<BlogItem> items = List.of(
                createItem(
                        section,
                        "Lorem ipsum dolor sit amet, consectetur",
                        "Maria",
                        "08 de Abril",
                        "2 min de leitura",
                        defaultImageUrl, // imageUrl
                        "Imagem ilustrativa 1",
                        defaultImageUrl, // iconUrl (usando a mesma imagem)
                        "Descrição detalhada do primeiro post. Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                ),
                createItem(
                        section,
                        "Segundo post de exemplo",
                        "João",
                        "10 de Abril",
                        "3 min de leitura",
                        defaultImageUrl,
                        "Imagem ilustrativa 2",
                        defaultImageUrl,
                        "Conteúdo do segundo post. Nullam euismod, nisl eget aliquam ultricies."
                ),
                createItem(
                        section,
                        "Terceiro post de exemplo",
                        "Ana",
                        "12 de Abril",
                        "4 min de leitura",
                        defaultImageUrl,
                        "Imagem ilustrativa 3",
                        defaultImageUrl,
                        "Terceira descrição. Vivamus lacinia odio vitae vestibulum."
                ),
                createItem(
                        section,
                        "Quarto post de exemplo",
                        "Genilton",
                        "12 de Abril",
                        "4 min de leitura",
                        defaultImageUrl,
                        "Imagem ilustrativa 4",
                        defaultImageUrl,
                        "Quarto conteúdo. Curabitur ullamcorper ultricies nisi."
                )
        );

        section.setItems(items);
        return repository.save(section);
    }

    private BlogSectionResponseDto createDefaultSectionResponse() {
        return toResponse(createDefaultSection());
    }

    private BlogItem createItem(
            BlogSection section,
            String title,
            String author,
            String date,
            String readingTime,
            String imageUrl,
            String imageDescription,
            String iconUrl,      // Novo campo
            String description   // Novo campo
    ) {
        BlogItem item = new BlogItem();
        item.setTitle(title);
        item.setAuthor(author);
        item.setDate(date);
        item.setReadingTime(readingTime);
        item.setImageUrl(imageUrl);
        item.setImageDescription(imageDescription);
        item.setIconUrl(iconUrl);       // Novo campo
        item.setDescription(description); // Novo campo
        item.setBlogSection(section);
        return item;
    }

    @Transactional
    public BlogSectionResponseDto addBlogItem(BlogItemCreateDto dto) throws IOException {
        BlogSection section = repository.findFirstByOrderByIdAsc()
                .orElseGet(this::createDefaultSection);

        BlogItem newItem = new BlogItem();
        newItem.setTitle(dto.title());
        newItem.setAuthor(dto.author());
        newItem.setDate(dto.date());
        newItem.setReadingTime(dto.readingTime());
        newItem.setImageDescription(dto.imageDescription());
        newItem.setDescription(dto.description()); // Novo campo
        newItem.setBlogSection(section);

        // Upload da imagem principal (existente)
        if (dto.file() != null && !dto.file().isEmpty()) {
            var uploadResult = localStorageService.salvar(dto.file());
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            newItem.setImageUrl(linkSanitizado);
            newItem.setFileName(uploadResult);
        }

        // Upload do ícone (novo)
        if (dto.iconFile() != null && !dto.iconFile().isEmpty()) {
            var iconUploadResult = localStorageService.salvar(dto.iconFile());
            var iconLink = baseUrl + "/publicos/" + iconUploadResult;
            newItem.setIconUrl(iconLink.replaceAll("\\s+", "_"));
        }

        section.getItems().add(newItem);
        repository.save(section);

        return toResponse(section);
    }

    @Transactional
    public BlogSectionResponseDto updateBlogItem(Long itemId, BlogItemUpdateDto dto) throws IOException {
        BlogSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Blog Section não encontrada"));

        BlogItem item = section.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item Blog não encontrado"));

        // Atualiza campos existentes
        if (dto.title() != null) item.setTitle(dto.title());
        if (dto.author() != null) item.setAuthor(dto.author());
        if (dto.date() != null) item.setDate(dto.date());
        if (dto.readingTime() != null) item.setReadingTime(dto.readingTime());
        if (dto.imageDescription() != null) item.setImageDescription(dto.imageDescription());
        if (dto.description() != null) item.setDescription(dto.description()); // Novo campo

        // Upload da imagem principal (existente)
        if (dto.file() != null && !dto.file().isEmpty()) {
            if (item.getFileName() != null) {
                localStorageService.deletar(item.getFileName());
            }
            var uploadResult = localStorageService.salvar(dto.file());
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setImageUrl(linkSanitizado);
            item.setFileName(uploadResult);
        }

        // Upload do ícone (novo)
        if (dto.iconFile() != null && !dto.iconFile().isEmpty()) {
            if (item.getIconUrl() != null) {
                // Opcional: deletar ícone antigo se necessário
            }
            var iconUploadResult = localStorageService.salvar(dto.iconFile());
            var iconLink = baseUrl + "/publicos/" + iconUploadResult;
            item.setIconUrl(iconLink.replaceAll("\\s+", "_"));
        }

        repository.save(section);
        return toResponse(section);
    }

    private BlogSectionResponseDto toResponse(BlogSection entity) {
        List<BlogItemResponseDto> items = entity.getItems().stream()
                .sorted(Comparator.comparing(BlogItem::getId))
                .map(item -> new BlogItemResponseDto(
                        item.getId(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getDate(),
                        item.getReadingTime(),
                        item.getImageUrl(),
                        item.getImageDescription(),
                        item.getIconUrl(),      // Novo campo
                        item.getDescription()    // Novo campo
                ))
                .toList();

        return new BlogSectionResponseDto(
                entity.getId(),
                entity.getTitle(),
                items
        );
    }
}