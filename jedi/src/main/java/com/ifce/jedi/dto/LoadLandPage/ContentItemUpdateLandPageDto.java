package com.ifce.jedi.dto.LoadLandPage;

import org.springframework.web.multipart.MultipartFile;

public class ContentItemUpdateLandPageDto {
    private Long id;
    private MultipartFile file;
    private String imgText;

    public ContentItemUpdateLandPageDto() {
    }

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

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public ContentItemUpdateLandPageDto(Long id, MultipartFile file, String imgText) {
        this.id = id;
        this.file = file;
        this.imgText = imgText;
    }
}
