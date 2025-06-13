package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.service.HeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/header")
public class HeaderController {

    @Autowired
    private HeaderService headerService;

    @GetMapping
    public ResponseEntity<HeaderResponseDto> getHeader() {
        HeaderResponseDto header = headerService.getHeader();
        return header == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(header);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HeaderResponseDto> updateHeader(@RequestBody HeaderDto dto) {
        return ResponseEntity.ok(headerService.updateHeader(dto));
    }
}
