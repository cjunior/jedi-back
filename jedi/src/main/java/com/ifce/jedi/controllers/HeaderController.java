package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.service.HeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/header")
public class HeaderController {

    @Autowired
    private HeaderService headerService;

    @GetMapping("/get")
    public ResponseEntity<HeaderResponseDto> getHeader() {
        HeaderResponseDto header = headerService.getHeader();
        return header == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(header);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<HeaderResponseDto> updateHeader(@ModelAttribute HeaderDto dto) throws IOException {
        return ResponseEntity.ok(headerService.updateHeader(dto));
    }
}
