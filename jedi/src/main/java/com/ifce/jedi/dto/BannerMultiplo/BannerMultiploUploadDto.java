package com.ifce.jedi.dto.BannerMultiplo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BannerMultiploUploadDto {
    private MultipartFile[] file;
    private List<String> linkUrl;
    private List<String> title;

    public MultipartFile[] getFile() {
        return file;
    }

    public void setFile(MultipartFile[] file) {
        this.file = file;
    }

    public List<String> getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(List<String> linkUrl) {
        this.linkUrl = linkUrl;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }
}
