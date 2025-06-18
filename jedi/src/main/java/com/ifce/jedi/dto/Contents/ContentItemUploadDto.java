package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ContentItemUploadDto {
    private List<MultipartFile> files;
    private List<String> imgTexts;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<String> getImgTexts() {
        return imgTexts;
    }

    public void setImgTexts(List<String> imgTexts) {
        this.imgTexts = imgTexts;
    }

    public ContentItemUploadDto() {
    }

    public ContentItemUploadDto(List<MultipartFile> files, List<String> imgTexts) {
        this.files = files;
        this.imgTexts = imgTexts;
    }
}
