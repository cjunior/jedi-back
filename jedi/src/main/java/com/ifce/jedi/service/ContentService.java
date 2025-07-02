package com.ifce.jedi.service;

import com.ifce.jedi.dto.Contents.*;
import com.ifce.jedi.model.SecoesSite.Contents.Content;
import com.ifce.jedi.model.SecoesSite.Contents.ContentItem;
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
    private MinioService minioService;


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
                content.getMainImageText(),
                slides
        );
    }

    @Transactional
    public ContentResponseDto updateContent(UpdateContentDto dto) throws IOException {
        Content content = contentRepository.findAll().stream().findFirst().orElseThrow(
                () -> new RuntimeException("Seção não encontrada.")
        );

        if(dto.getTitle() != null){
            content.setTitle(dto.getTitle());
        }
        if(dto.getSubTitle() != null){
            content.setSubTitle(dto.getSubTitle());

        }
        if(dto.getDescription() != null){
            content.setDescription(dto.getDescription());

        }
        if(dto.getMainImageText() != null){
            content.setMainImageText(dto.getMainImageText());

        }
        if(dto.getMainImage() != null && !dto.getMainImage().isEmpty()){
            if (content.getStorageNamefile() != null) {
                minioService.deleteImage(content.getStorageNamefile());
            }
            var uploadResult = minioService.create(dto.getMainImage());
            content.setMainImageUrl(uploadResult.get("url"));
            content.setStorageNamefile(uploadResult.get("filename"));
        }

        Content updated = contentRepository.save(content);
        return toResponse(updated);
    }

    @Transactional
    public ContentResponseDto addSlide(MultipartFile file, ContentItemDto dto) throws IOException {
        Content content = contentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Seção não encontrada"));


        ContentItem item = new ContentItem();
        var uploadResult = minioService.create(file);
        item.setImgUrl(uploadResult.get("url"));
        item.setStorageFilename(uploadResult.get("filename"));
        item.setImgText(dto.getImgTexts());
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
        content.setMainImageText(dto.getMainImageText());

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

        minioService.deleteImage(itemToRemove.getStorageFilename());
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

        if (dto.getImgTexts() != null) {
            item.setImgText(dto.getImgTexts());
        }

        if (file != null && !file.isEmpty()) {
            if (item.getStorageFilename() != null) {
                minioService.deleteImage(item.getStorageFilename());
            }
            var uploadResult = minioService.create(file);
            item.setImgUrl(uploadResult.get("url"));
            item.setStorageFilename(uploadResult.get("filename"));
        }

        contentRepository.save(content);

        return new ContentItemResponseDto(
                item.getId(),
                item.getImgUrl(),
                item.getImgText()
        );
    }
}
