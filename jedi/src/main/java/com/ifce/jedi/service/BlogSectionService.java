package com.ifce.jedi.service;

import com.ifce.jedi.dto.Blog.*;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogItem;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogSection;
import com.ifce.jedi.repository.BlogSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
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

    private BlogSection createDefaultSection() {
        BlogSection section = new BlogSection();
        section.setTitle("Últimas postagens do blog");

        List<BlogItem> items = List.of(
                createItem(section, "Lorem ipsum dolor sit amet, consectetur", "Maria", "08 de Abril", "2 min de leitura",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png",
                        "Imagem ilustrativa 1"),
                createItem(section, "Segundo post de exemplo", "João", "10 de Abril", "3 min de leitura",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png",
                        "Imagem ilustrativa 2"),
                createItem(section, "Terceiro post de exemplo", "Ana", "12 de Abril", "4 min de leitura",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png",
                        "Imagem ilustrativa 3"),
                createItem(section, "Quarto post de exemplo", "Genilton", "12 de Abril", "4 min de leitura",
                        "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png",
                        "Imagem ilustrativa 4")
        );

        section.setItems(items);
        return repository.save(section);
    }

    private BlogSectionResponseDto createDefaultSectionResponse() {
        return toResponse(createDefaultSection());
    }

    private BlogItem createItem(BlogSection section, String title, String author, String date,
                                String readingTime, String imageUrl, String imageDescription) {
        BlogItem item = new BlogItem();
        item.setTitle(title);
        item.setAuthor(author);
        item.setDate(date);
        item.setReadingTime(readingTime);
        item.setImageUrl(imageUrl);
        item.setImageDescription(imageDescription);
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
            newItem.setCloudinaryPublicId(uploadResult);
        }

        // Upload do ícone (novo)
        if (dto.iconFile() != null && !dto.iconFile().isEmpty()) {
            var iconUploadResult = localStorageService.salvar(dto.iconFile());
            var linkCru = baseUrl + "/publicos/" + iconUploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            newItem.setIconUrl(linkSanitizado);
            newItem.setIconCloudinaryPublicId(iconUploadResult);
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

        // Atualiza campos básicos
        if (dto.title() != null) item.setTitle(dto.title());
        if (dto.author() != null) item.setAuthor(dto.author());
        if (dto.date() != null) item.setDate(dto.date());
        if (dto.readingTime() != null) item.setReadingTime(dto.readingTime());
        if (dto.imageDescription() != null) item.setImageDescription(dto.imageDescription());
        if (dto.description() != null) item.setDescription(dto.description()); // Novo campo

        // Atualiza imagem principal (existente)
        if (dto.file() != null && !dto.file().isEmpty()) {
            if (item.getCloudinaryPublicId() != null) {
                localStorageService.deletar(item.getCloudinaryPublicId());
            }
            var uploadResult = localStorageService.salvar(dto.file());
            var linkCru = baseUrl + "/publicos/" + uploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setImageUrl(linkSanitizado);
            item.setCloudinaryPublicId(uploadResult);
        }

        // Atualiza ícone (novo)
        if (dto.iconFile() != null && !dto.iconFile().isEmpty()) {
            if (item.getIconCloudinaryPublicId() != null) {
                localStorageService.deletar(item.getIconCloudinaryPublicId());
            }
            var iconUploadResult = localStorageService.salvar(dto.iconFile());
            var linkCru = baseUrl + "/publicos/" + iconUploadResult;
            var linkSanitizado = linkCru.replaceAll("\\s+", "_");
            item.setIconUrl(linkSanitizado);
            item.setIconCloudinaryPublicId(iconUploadResult);
        }

        repository.save(section);
        return toResponse(section);
    }
    @Transactional
    public BlogItemResponseDto getBlogItemById(Long itemId) {
        BlogSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Blog Section não encontrada"));

        BlogItem item = section.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item Blog não encontrado"));

        return new BlogItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getAuthor(),
                item.getDate(),
                item.getReadingTime(),
                item.getImageUrl(),
                item.getImageDescription(),
                item.getIconUrl(),
                item.getDescription()
        );
    }
    @Transactional
    public void deleteBlogItem(Long itemId) throws IOException {
        BlogSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Blog Section não encontrada"));

        BlogItem item = section.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item Blog não encontrado"));

        // Deleta a imagem principal do Cloudinary (se existir)
        if (item.getCloudinaryPublicId() != null) {
            localStorageService.deletar(item.getCloudinaryPublicId());
        }

        // Deleta o ícone do Cloudinary (se existir)
        if (item.getIconCloudinaryPublicId() != null) {
            localStorageService.deletar(item.getIconCloudinaryPublicId());
        }

        // Remove o item da lista e salva a seção
        section.getItems().remove(item);
        repository.save(section);
    }
    @Transactional
    public Page<BlogItemResponseDto> getPaginatedBlogItems(Pageable pageable, String searchTerm) {
        Optional<BlogSection> entity = repository.findFirstByOrderByIdAsc();

        if (entity.isEmpty()) {
            return Page.empty(); // Retorna página vazia se não houver seção
        }

        List<BlogItem> filteredItems = entity.get().getItems().stream()
                .filter(item -> searchTerm == null || searchTerm.isBlank() ||
                        item.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .sorted(Comparator.comparing(BlogItem::getId).reversed())
                .toList();

        int totalItems = filteredItems.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<BlogItem> pageItems;
        if (startItem >= totalItems) {
            pageItems = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, totalItems);
            pageItems = filteredItems.subList(startItem, toIndex);
        }

        List<BlogItemResponseDto> dtos = pageItems.stream()
                .map(item -> new BlogItemResponseDto(
                        item.getId(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getDate(),
                        item.getReadingTime(),
                        item.getImageUrl(),
                        item.getImageDescription(),
                        item.getIconUrl(),
                        item.getDescription()
                ))
                .toList();

        return new PageImpl<>(dtos, pageable, totalItems);
    }

    private BlogSectionResponseDto toResponse(BlogSection entity) {
        List<BlogItemResponseDto> items = entity.getItems().stream()
                .sorted(Comparator.comparing(BlogItem::getId).reversed()) // 🔥 Ordem decrescente por ID
                .map(item -> new BlogItemResponseDto(
                        item.getId(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getDate(),
                        item.getReadingTime(),
                        item.getImageUrl(),
                        item.getImageDescription(),
                        item.getIconUrl(),
                        item.getDescription()
                ))
                .toList();

        return new BlogSectionResponseDto(
                entity.getId(),
                entity.getTitle(),
                items
        );
    }
}