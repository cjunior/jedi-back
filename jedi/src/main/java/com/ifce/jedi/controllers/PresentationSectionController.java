package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.PresentationSection.*;
import com.ifce.jedi.service.PresentationSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/presentation-section")
public class PresentationSectionController {

    @Autowired
    private PresentationSectionService service;

    @GetMapping("/get")
    public ResponseEntity<PresentationSectionResponseDto> get() {
        PresentationSectionResponseDto section = service.get();
        return section == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(section);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PresentationSectionResponseDto> create(@RequestBody PresentationSectionDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PresentationSectionResponseDto> update(@RequestBody PresentationSectionUpdateDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PutMapping(value = "/update-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PresentationSectionResponseDto> updateImage(@ModelAttribute PresentationSectionImageUploadDto dto) throws IOException {
        return ResponseEntity.ok(service.updateImage(dto.getFile()));
    }
}