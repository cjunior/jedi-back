package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ContentItemDto{
    private String imgTexts;
    private MultipartFile files;

    public ContentItemDto() {
    }

    public String getImgTexts() {
        return imgTexts;
    }

    public void setImgTexts(String imgTexts) {
        this.imgTexts = imgTexts;
    }

    public MultipartFile getFiles() {
        return files;
    }

    public void setFiles(MultipartFile files) {
        this.files = files;
    }

    public ContentItemDto(String imgTexts, MultipartFile files) {
        this.imgTexts = imgTexts;
        this.files = files;
    }
}
