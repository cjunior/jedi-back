package com.ifce.jedi.service;

import com.ifce.jedi.dto.Blog.*;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogItem;
import com.ifce.jedi.model.SecoesSite.BlogSection.BlogSection;
import com.ifce.jedi.repository.BlogSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogSectionService {

    @Autowired
    private BlogSectionRepository repository;

    @Autowired
    private LocalStorageService localStorageService;

    @Transactional
    public BlogSectionResponseDto get() {
        Optional<BlogSection> entity = repository.findFirstByOrderByIdAsc();
        return entity.map(this::toResponse).orElseGet(this::createDefaultSection);
    }

    private BlogSectionResponseDto createDefaultSection() {
        BlogSection section = new BlogSection();
        section.setTitle("Últimas postagens do blog");

        List<BlogItem> items = List.of(
                createItem(section, "Lorem ipsum dolor sit amet, consectetur", "Maria", "08 de Abril", "2 min de leitura", "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png", "Imagem ilustrativa 1"),
                createItem(section, "Segundo post de exemplo", "João", "10 de Abril", "3 min de leitura", "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png", "Imagem ilustrativa 2"),
                createItem(section, "Terceiro post de exemplo", "Ana", "12 de Abril", "4 min de leitura", "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png", "Imagem ilustrativa 3"),
                createItem(section, "Quarto post de exemplo", "Genilton", "12 de Abril", "4 min de leitura", "https://res.cloudinary.com/dp98r2imm/image/upload/v1749996488/d750ae3dcaf62a93289de01f9b7384e86d42784e_kmfia6.png", "Imagem ilustrativa 4")
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
    public BlogSectionResponseDto updateBlogItem(Long itemId, BlogItemUpdateDto dto) throws IOException {
        BlogSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Blog Section não encontrada"));

        BlogItem item = section.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item Blog não encontrado"));

        // Atualiza campos textuais
        if (dto.title() != null) item.setTitle(dto.title());
        if (dto.author() != null) item.setAuthor(dto.author());
        if (dto.date() != null) item.setDate(dto.date());
        if (dto.readingTime() != null) item.setReadingTime(dto.readingTime());
        if (dto.imageDescription() != null) item.setImageDescription(dto.imageDescription());

        // Atualiza imagem se for fornecida
        if (dto.file() != null && !dto.file().isEmpty()) {
            if (item.getFileName() != null) {
                localStorageService.deletar(item.getFileName());
            }
            var uploadResult = localStorageService.salvar(dto.file());
            item.setImageUrl(localStorageService.carregar(uploadResult).toString());
            item.setFileName(uploadResult);
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