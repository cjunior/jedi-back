package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Contents.ContentDto;
import com.ifce.jedi.dto.Contents.ContentItemDto;
import com.ifce.jedi.dto.Contents.ContentResponseDto;
import com.ifce.jedi.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @GetMapping("/get")
    public ResponseEntity<ContentResponseDto> get() {
        ContentResponseDto content = contentService.getContent();
        return content == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(content);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContentResponseDto> update(@ModelAttribute ContentDto dto) {
        return ResponseEntity.ok(contentService.updateContent(dto));
    }

    @PostMapping(value = "/slides/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSlide(@ModelAttribute ContentItemDto form) throws IOException {

        List<MultipartFile> files = form.getFiles();
        List<String> igmText = form.getImgTexts();

        if (files.size() != igmText.size()) {
            return ResponseEntity.badRequest().body("Número de arquivos e textos não correspondem.");
        }

        for (int i = 0; i < igmText.size(); i++) {
            ContentItemDto dto = new ContentItemDto(igmText.get(i));
            bannerService.addSlide(files[i], dto);
        }

        return ResponseEntity.ok("Itens adicionados com sucesso!");
    }
}
