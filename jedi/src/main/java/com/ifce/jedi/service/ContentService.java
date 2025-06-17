package com.ifce.jedi.service;

import com.ifce.jedi.dto.Banner.BannerItemResponseDto;
import com.ifce.jedi.dto.Banner.BannerResponseDto;
import com.ifce.jedi.dto.Contents.ContentDto;
import com.ifce.jedi.dto.Contents.ContentItemResponseDto;
import com.ifce.jedi.dto.Contents.ContentResponseDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.model.SecoesSite.Banner.Banner;
import com.ifce.jedi.model.SecoesSite.Contents.Content;
import com.ifce.jedi.model.SecoesSite.Header.Header;
import com.ifce.jedi.repository.ContentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                new ContentItemResponseDto(s.getId(), s.getImgUrl(), s.getImgText(), s.getMain())
        ).collect(Collectors.toList());

        return new ContentResponseDto(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
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
}
