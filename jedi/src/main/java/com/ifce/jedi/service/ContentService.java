package com.ifce.jedi.service;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemDto;
import com.ifce.jedi.dto.Banner.BannerItemResponseDto;
import com.ifce.jedi.dto.Banner.BannerResponseDto;
import com.ifce.jedi.dto.Contents.*;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.model.SecoesSite.Banner.Banner;
import com.ifce.jedi.model.SecoesSite.Banner.BannerItem;
import com.ifce.jedi.model.SecoesSite.Contents.Content;
import com.ifce.jedi.model.SecoesSite.Contents.ContentItem;
import com.ifce.jedi.model.SecoesSite.Header.Header;
import com.ifce.jedi.repository.ContentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private CloudinaryService cloudinaryService;


    @Transactional
    public ContentResponseDto getContent() {
        List<Content> contents = contentRepository.findAll();
        if (contents.isEmpty()) return null;
        return toResponse(contents.get(0));
    }


    private ContentResponseDto toResponse(Content content) {
        List<ContentItemResponseDto> slides = content.getImgCarousel().stream().map(s ->
                new ContentItemResponseDto(s.getId(), s.getImgUrl(), s.getImgText())
        ).collect(Collectors.toList());

        return new ContentResponseDto(
                content.getId(),
                content.getTitle(),
                content.getSubTitle(),
                content.getDescription(),
                content.getMainImageUrl(),
                slides
        );
    }

    @Transactional
    public ContentResponseDto updateContent(UpdateContentDto dto) throws IOException {
        Content content = contentRepository.findAll().stream().findFirst().orElseThrow(
                () -> new RuntimeException("Seção não encontrada.")
        );

        content.setTitle(dto.getTitle());
        content.setSubTitle(dto.getSubTitle());
        content.setDescription(dto.getDescription());
        if(dto.getMainImage() != null && !dto.getMainImage().isEmpty()){
            if (content.getCloudinaryPublicId() != null) {
                cloudinaryService.deleteImage(content.getCloudinaryPublicId());
            }
            var uploadResult = cloudinaryService.uploadImage(dto.getMainImage());
            content.setMainImageUrl(uploadResult.get("url"));
            content.setCloudinaryPublicId(uploadResult.get("public_id"));
        }

        Content updated = contentRepository.save(content);
        return toResponse(updated);
    }

    @Transactional
    public ContentResponseDto addSlide(MultipartFile file, ContentItemDto dto) throws IOException {
        Content content = contentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Seção não encontrada"));


        ContentItem item = new ContentItem();
        var uploadResult = cloudinaryService.uploadImage(file);
        item.setImgUrl(uploadResult.get("url"));
        item.setCloudinaryPublicId(uploadResult.get("public_id"));
        item.setImgText(dto.imgTexts());
        item.setContent(content);
        content.getImgCarousel().add(item);


        contentRepository.save(content);
        return toResponse(content);
    }


    @Transactional
    public ContentResponseDto createContent(ContentDto dto) {
        Content content = new Content();
        content.setTitle(dto.getTitle());
        content.setSubTitle(dto.getSubTitle());
        content.setDescription(dto.getDescription());
        content.setMainImageUrl(dto.getMainImage());

        List<ContentItem> items = dto.getItems().stream().map(s -> {
            ContentItem item = new ContentItem();
            item.setImgUrl(s.getImgUrl());
            item.setImgText(s.getImgText());
            item.setContent(content);
            return item;
        }).toList();

        content.setImgCarousel(items);
        Content saved = contentRepository.save(content);

        return toResponse(content);
    }

    @Transactional
    public ContentResponseDto deleteSlide(Long id) throws IOException {
        Content content = contentRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Content section não encontrado"));

        ContentItem itemToRemove = content.getImgCarousel().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Slide não encontrado"));

        cloudinaryService.deleteImage(itemToRemove.getCloudinaryPublicId());
        content.getImgCarousel().remove(itemToRemove);

        contentRepository.save(content);

        return toResponse(content);
    }

    @Transactional
    public ContentItemResponseDto updateSlide(Long id, MultipartFile file, ContentItemDto dto) throws IOException {
        Content content = contentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Content section não encontrado."));

        ContentItem item = content.getImgCarousel().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Slide não encontrado."));

        // Atualiza o texto (DEBUG)
        System.out.println("Texto antes: " + item.getImgText());
        System.out.println("Texto recebido: " + dto.imgTexts());

        item.setImgText(dto.imgTexts()); // Atualiza sempre, independente do arquivo

        // Atualiza a imagem (se houver)
        if (file != null && !file.isEmpty()) {
            if (item.getCloudinaryPublicId() != null) {
                cloudinaryService.deleteImage(item.getCloudinaryPublicId());
            }
            var uploadResult = cloudinaryService.uploadImage(file);
            item.setImgUrl(uploadResult.get("url"));
            item.setCloudinaryPublicId(uploadResult.get("public_id"));
        }

        contentRepository.save(content); // Força a persistência

        // Verifica se o texto foi atualizado
        System.out.println("Texto depois: " + item.getImgText());

        return new ContentItemResponseDto(
                item.getId(),
                item.getImgUrl(),
                item.getImgText()
        );
    }
}
