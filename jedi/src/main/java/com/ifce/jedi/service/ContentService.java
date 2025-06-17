package com.ifce.jedi.service;

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
                content.getDescription(),
                content.getMainImageUrl(),
                slides
        );
    }

    @Transactional
    public ContentResponseDto updateContent(ContentDto dto) {
        Content content = contentRepository.findAll().stream().findFirst().orElseThrow(
                () -> new RuntimeException("Seção não encontrada.")
        );

        content.setTitle(dto.getTitle());
        content.setDescription(dto.getDescription());

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
        item.setImgText(dto.getImgTexts());
        item.setContent(content);
        content.getImgCarousel().add(item);


        contentRepository.save(content);
        return toResponse(content);
    }

    public void createContent(ContentDto dto) {
    }
}
