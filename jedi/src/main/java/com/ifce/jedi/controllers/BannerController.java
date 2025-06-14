package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.*;
import com.ifce.jedi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/get")
    public ResponseEntity<BannerResponseDto> get() {
        BannerResponseDto banner = bannerService.getBanner();
        return banner == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(banner);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BannerResponseDto> update(@RequestBody BannerUpdateDto dto) {
        return ResponseEntity.ok(bannerService.updateBanner(dto));
    }

    @PostMapping("/slides/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BannerItemResponseDto> addSlide(@RequestBody BannerItemDto dto) {
        return ResponseEntity.ok(bannerService.addSlide(dto));
    }

    @PutMapping("/slide/{slideId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BannerItemResponseDto> updateSlide(
            @PathVariable Long slideId,
            @RequestBody BannerItemDto dto) {
        return ResponseEntity.ok(bannerService.updateSlide(slideId, dto));
    }
}