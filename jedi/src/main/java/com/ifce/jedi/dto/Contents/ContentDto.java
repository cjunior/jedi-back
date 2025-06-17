package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ContentDto {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContentDto() {
    }

    public ContentDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
