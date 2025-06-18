package com.ifce.jedi.dto.Banner;

import org.springframework.web.multipart.MultipartFile;

public class BannerItemUpdate {
    private Long id;
    private String buttonText;
    private String buttonUrl;
    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public BannerItemUpdate() {
    }

    public BannerItemUpdate(Long id, String buttonText, String buttonUrl, MultipartFile file) {
        this.id = id;
        this.buttonText = buttonText;
        this.buttonUrl = buttonUrl;
        this.file = file;
    }
}