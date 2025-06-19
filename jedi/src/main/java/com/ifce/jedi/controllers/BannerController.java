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
import java.util.List;

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
    public ResponseEntity<?> addSlide(@ModelAttribute BannerSlideUploadDto form) throws IOException {

        MultipartFile[] files = form.getFile();
        List<String> buttonText = form.getButtonText();
        List<String> buttonUrl = form.getButtonUrl();

        for (int i = 0; i < buttonText.size(); i++) {
            String url = (buttonUrl != null && i < buttonUrl.size()) ? buttonUrl.get(i) : "";
            BannerItemDto dto = new BannerItemDto(buttonText.get(i), url);
            bannerService.addSlide(files[i], dto);
        }

        return ResponseEntity.ok("Itens adicionados com sucesso!");
    }



    @PutMapping(value = "/slide/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSlide(@ModelAttribute BannerSlideUpdateDto dto) throws IOException {

        List<Long> slideIds = dto.getSlideId();
        MultipartFile[] files = dto.getFile();
        List<String> buttonText = dto.getButtonText();
        List<String> buttonUrl = dto.getButtonUrl();

        for (int i = 0; i < slideIds.size(); i++) {
            String url = (buttonUrl != null && i < buttonUrl.size()) ? buttonUrl.get(i) : "";
            String text = (buttonText != null && i < buttonText.size()) ? buttonText.get(i) : "";
            BannerItemDto itemDto = new BannerItemDto(text, url);
            bannerService.updateSlide(slideIds.get(i), files[i], itemDto);
        }

        return ResponseEntity.ok("Slides atualizados com sucesso.");
    }



    @DeleteMapping("/slide/{slideId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSlide(
            @PathVariable List<Long> slideId) throws IOException {
        for (int i = 0; i < slideId.size(); i++)
            bannerService.deleteSlide(slideId.get(i));
        return ResponseEntity.ok().body("Imagens excluídas com sucesso");
    }
}