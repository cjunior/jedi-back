package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.*;
import com.ifce.jedi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(value = "/slides/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BannerItemResponseDto> addSlide(
            @RequestPart("file") MultipartFile file,
            @RequestPart("buttonText") String buttonText,
            @RequestPart(value = "buttonUrl", required = false) String buttonUrl) throws IOException {

        BannerItemDto dto = new BannerItemDto(buttonText, buttonUrl);
        return ResponseEntity.ok(bannerService.addSlide(file, dto));
    }


    @PutMapping(value ="/slide/{slideId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BannerItemResponseDto> updateSlide(
            @PathVariable Long slideId,
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart("buttonText") String buttonText,
            @RequestPart(value = "buttonUrl", required = false) String buttonUrl) throws IOException {

        BannerItemDto dto = new BannerItemDto(buttonText, buttonUrl);
        return ResponseEntity.ok(bannerService.updateSlide(slideId, file, dto));
    }


    @DeleteMapping("/slide/{slideId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BannerResponseDto> deleteSlide(
            @PathVariable Long slideId) throws IOException {
        return ResponseEntity.ok(bannerService.deleteSlide(slideId));
    }
}