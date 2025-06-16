package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.PresentationSection.PresentationSectionDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionResponseDto;
import com.ifce.jedi.service.PresentationSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presentation-section")
public class PresentationSectionController {

    @Autowired
    private PresentationSectionService service;

    @GetMapping("/get")
    public ResponseEntity<PresentationSectionResponseDto> getPresentationSection() {
        PresentationSectionResponseDto section = service.getPresentationSection();
        return section == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(section);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PresentationSectionResponseDto> updatePresentationSection(@RequestBody PresentationSectionDto dto) {
        return ResponseEntity.ok(service.updatePresentationSection(dto));
    }
}
