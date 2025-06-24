package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.FaqSection.*;
import com.ifce.jedi.service.FaqSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faq-section")
public class FaqSectionController {

    @Autowired
    private FaqSectionService service;

    @GetMapping("/get")
    public ResponseEntity<FaqSectionResponseDto> get() {
        return ResponseEntity.ok(service.get());
    }

    @PutMapping("/item/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqSectionResponseDto> updateItem(
            @PathVariable Long itemId,
            @RequestBody FaqItemUpdateDto dto) {
        return ResponseEntity.ok(service.updateItem(itemId, dto));
    }
    @PutMapping("/update-header")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqSectionResponseDto> updateHeader(
            @RequestBody FaqSectionHeaderUpdateDto dto) {
        return ResponseEntity.ok(service.updateHeader(dto));
    }
    // FaqSectionController.java
    @PostMapping("/items")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqSectionResponseDto> addItem(
            @RequestBody FaqItemCreateDto dto) {
        return ResponseEntity.ok(service.addItem(dto));
    }
}