package com.ifce.jedi.dto.Contents;


import java.util.List;

public class ContentItemDto{
    private String imgTexts;

    public String getImgTexts() {
        return imgTexts;
    }

    public void setImgTexts(String imgTexts) {
        this.imgTexts = imgTexts;
    }

    public ContentItemDto() {
    }

    public ContentItemDto(String imgTexts) {
        this.imgTexts = imgTexts;
    }
}
