package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Blog.*;
import com.ifce.jedi.service.BlogSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<BlogItemResponseDto>> getPaginatedBlogItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchTerm) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(service.getPaginatedBlogItems(pageable, searchTerm));
    }

    @PutMapping(value = "/item/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'BLOG')")
    public ResponseEntity<BlogSectionResponseDto> updateBlogItem(
            @PathVariable Long itemId,
            @ModelAttribute BlogItemUpdateDto dto) throws IOException {
        return ResponseEntity.ok(service.updateBlogItem(itemId, dto));
    }
    @PostMapping(value = "/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'BLOG')")
    public ResponseEntity<BlogSectionResponseDto> createBlogItem(
            @ModelAttribute BlogItemCreateDto dto) throws IOException {
        return ResponseEntity.ok(service.addBlogItem(dto));
    }
    @GetMapping("/item/{itemId}")
    public ResponseEntity<BlogItemResponseDto> getBlogItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(service.getBlogItemById(itemId));
    }
    @DeleteMapping("/item/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'BLOG')")
    public ResponseEntity<Void> deleteBlogItem(@PathVariable Long itemId) throws IOException {
        service.deleteBlogItem(itemId);
        return ResponseEntity.noContent().build();
    }
}