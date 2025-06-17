package com.ifce.jedi.dto.Banner;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BannerSlideUpdateDto {
    private List<Long> slideId;
    private MultipartFile[] file;
    private List<String> buttonText;
    private List<String> buttonUrl;

    public BannerSlideUpdateDto(List<Long> slideId, MultipartFile[] file, List<String> buttonText, List<String> buttonUrl) {
        this.slideId = slideId;
        this.file = file;
        this.buttonText = buttonText;
        this.buttonUrl = buttonUrl;
    }

    // Getters e Setters
    public List<Long> getSlideId() { return slideId; }
    public void setSlideId(List<Long> slideId) { this.slideId = slideId; }

    public MultipartFile[] getFile() { return file; }
    public void setFile(MultipartFile[] file) { this.file = file; }

    public List<String> getButtonText() { return buttonText; }
    public void setButtonText(List<String> buttonText) { this.buttonText = buttonText; }

    public List<String> getButtonUrl() { return buttonUrl; }
    public void setButtonUrl(List<String> buttonUrl) { this.buttonUrl = buttonUrl; }
}
