package com.ifce.jedi.service;

import com.ifce.jedi.dto.Blog.*;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogItem;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogSection;
import com.ifce.jedi.repository.BlogSectionRepository;
import com.ifce.jedi.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BlogSectionService {

    @Autowired
    private BlogSectionRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public BlogSectionResponseDto get() {
        Optional<BlogSection> entity = repository.findFirstByOrderByIdAsc();
        return entity.map(this::toResponse).orElseGet(this::createDefaultSection);
    }

    private BlogSectionResponseDto createDefaultSection() {
        BlogSection section = new BlogSection();
        section.setTitle("Últimas postagens do blog");

        List<BlogItem> items = List.of(
                createItem(section, "Lorem ipsum dolor sit amet, consectetur", "Maria", "08 de Abril", "2 min de leitura", "https://exemplo.com/imagem1.jpg", "Imagem ilustrativa 1"),
                createItem(section, "Segundo post de exemplo", "João", "10 de Abril", "3 min de leitura", "https://exemplo.com/imagem2.jpg", "Imagem ilustrativa 2"),
                createItem(section, "Terceiro post de exemplo", "Ana", "12 de Abril", "4 min de leitura", "https://exemplo.com/imagem3.jpg", "Imagem ilustrativa 3")
        );

        section.setItems(items);
        BlogSection saved = repository.save(section);
        return toResponse(saved);
    }

    private BlogItem createItem(BlogSection section, String title, String author, String date, String readingTime, String imageUrl, String imageDescription) {
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
    public BlogSectionResponseDto updateItemImage(Long itemId, MultipartFile file) throws IOException {
        BlogSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Blog Section não encontrada"));

        BlogItem item = section.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item Blog não encontrado"));

        if (item.getCloudinaryPublicId() != null) {
            cloudinaryService.deleteImage(item.getCloudinaryPublicId());
        }

        var uploadResult = cloudinaryService.uploadImage(file);
        item.setImageUrl(uploadResult.get("url"));
        item.setCloudinaryPublicId(uploadResult.get("public_id"));

        repository.save(section);
        return toResponse(section);
    }

    private BlogSectionResponseDto toResponse(BlogSection entity) {
        List<BlogItemResponseDto> items = entity.getItems().stream()
                .map(item -> new BlogItemResponseDto(
                        item.getId(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getDate(),
                        item.getReadingTime(),
                        item.getImageUrl(),
                        item.getImageDescription()
                ))
                .toList();

        return new BlogSectionResponseDto(
                entity.getId(),
                entity.getTitle(),
                items
        );
    }
}