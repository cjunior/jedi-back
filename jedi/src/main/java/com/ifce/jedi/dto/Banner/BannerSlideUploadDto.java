package com.ifce.jedi.dto.Banner;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BannerSlideUploadDto {
    private MultipartFile[] file;
    private List<String> buttonText;
    private List<String> buttonUrl;

    public MultipartFile[] getFile() { return file; }
    public void setFile(MultipartFile[] file) { this.file = file; }

    public List<String> getButtonText() { return buttonText; }
    public void setButtonText(List<String> buttonText) { this.buttonText = buttonText; }

    public List<String> getButtonUrl() { return buttonUrl; }
    public void setButtonUrl(List<String> buttonUrl) { this.buttonUrl = buttonUrl; }
}

