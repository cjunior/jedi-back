package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.BannerItemDto;
import com.ifce.jedi.dto.Banner.BannerSlideUpdateDto;
import com.ifce.jedi.dto.Contents.*;
import com.ifce.jedi.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @GetMapping("/get")
    public ResponseEntity<ContentResponseDto> get() {
        ContentResponseDto content = contentService.getContent();
        return content == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(content);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContentResponseDto> update(@ModelAttribute UpdateContentDto dto) throws IOException {
        return ResponseEntity.ok(contentService.updateContent(dto));
    }

    @PutMapping(value = "/slide/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSlide(@ModelAttribute ContentUpdateWrapperDto wrapperDto) throws IOException {
        List<ContentItemUpdateDto> contentItems = wrapperDto.getItems();

        for (ContentItemUpdateDto item : contentItems) {
            ContentItemDto contentItemDto = new ContentItemDto(item.getImgsText());
            contentService.updateSlide(item.getId(), item.getFile(), contentItemDto);
        }

        return ResponseEntity.ok("Slides atualizados com sucesso.");
    }


    @PostMapping(value = "/slides/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSlide(@ModelAttribute ContentItemUploadDto form) throws IOException {

        List<MultipartFile> files = form.getFiles();
        List<String> igmText = form.getImgTexts();

        if (files.size() != igmText.size()) {
            return ResponseEntity.badRequest().body("Número de arquivos e textos não correspondem.");
        }

        for (int i = 0; i < igmText.size(); i++) {
            ContentItemDto dto = new ContentItemDto(igmText.get(i));
            contentService.addSlide(files.get(i), dto);
        }

        return ResponseEntity.ok("Itens adicionados com sucesso!");
    }

    @DeleteMapping("/slide/{slideId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSlide(
            @PathVariable List<Long> slideId) throws IOException {
        for (int i = 0; i < slideId.size(); i++)
            contentService.deleteSlide(slideId.get(i));
        return ResponseEntity.ok().body("Imagens excluídas com sucesso");
    }
}
