package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UpdateContentDto {
    private String title;
    private String subTitle;
    private String description;
    private MultipartFile mainImage;
    private String mainImageText;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getMainImage() {
        return mainImage;
    }

    public void setMainImage(MultipartFile mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImageText() {
        return mainImageText;
    }

    public void setMainImageText(String mainImageText) {
        this.mainImageText = mainImageText;
    }

    public UpdateContentDto() {
    }

    public UpdateContentDto(String title, String subTitle, String description, MultipartFile mainImage, String mainImageText) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.mainImage = mainImage;
        this.mainImageText = mainImageText;
    }
}
