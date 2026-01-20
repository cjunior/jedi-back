package com.ifce.jedi.dto.BannerMultiplo;

import org.springframework.web.multipart.MultipartFile;

public class BannerMultiploUpdateDto {
    private MultipartFile file;
    private String linkUrl;
    private String title;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
