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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HeaderResponseDto> updateHeader(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart("text1") String text1,
            @RequestPart("text2") String text2,
            @RequestPart("text3") String text3,
            @RequestPart("text4") String text4,
            @RequestPart("buttonText") String buttnText) throws IOException {
        HeaderDto dto = new HeaderDto(text1, text2, text3, text4, buttnText);
        return ResponseEntity.ok(headerService.updateHeader(file, dto));
    }
}
