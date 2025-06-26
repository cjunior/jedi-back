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

    @PutMapping(value = "/item/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogSectionResponseDto> updateBlogItem(
            @PathVariable Long itemId,
            @ModelAttribute BlogItemUpdateDto dto) throws IOException {
        return ResponseEntity.ok(service.updateBlogItem(itemId, dto));
    }
    @PostMapping(value = "/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogSectionResponseDto> createBlogItem(
            @ModelAttribute BlogItemCreateDto dto) throws IOException {
        return ResponseEntity.ok(service.addBlogItem(dto));
    }
    @GetMapping("/item/{itemId}")
    public ResponseEntity<BlogItemResponseDto> getBlogItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(service.getBlogItemById(itemId));
    }
    @DeleteMapping("/item/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBlogItem(@PathVariable Long itemId) throws IOException {
        service.deleteBlogItem(itemId);
        return ResponseEntity.noContent().build();
    }
}