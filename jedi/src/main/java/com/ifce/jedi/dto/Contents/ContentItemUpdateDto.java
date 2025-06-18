package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ContentItemUpdateDto {

    private Long id;
    private MultipartFile file;
    private String imgsText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getImgsText() {
        return imgsText;
    }

    public void setImgsText(String imgsText) {
        this.imgsText = imgsText;
    }

    public ContentItemUpdateDto() {
    }

    public ContentItemUpdateDto(Long id, MultipartFile file, String imgsText) {
        this.id = id;
        this.file = file;
        this.imgsText = imgsText;
    }
}
