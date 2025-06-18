package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Blog.*;
import com.ifce.jedi.service.BlogSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/blog-section")
public class BlogSectionController {

    @Autowired
    private BlogSectionService service;

    @GetMapping("/get")
    public ResponseEntity<BlogSectionResponseDto> get() {
        return ResponseEntity.ok(service.get());
    }

    @PutMapping(value = "/item/{itemId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogSectionResponseDto> updateItemImage(
            @PathVariable Long itemId,
            @ModelAttribute BlogItemImageUploadDto dto) throws IOException {
        return ResponseEntity.ok(service.updateItemImage(itemId, dto.file()));
    }

    @PostMapping("/reset-to-default")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogSectionResponseDto> resetToDefault() {
        return ResponseEntity.ok(service.resetToDefault());
    }
}