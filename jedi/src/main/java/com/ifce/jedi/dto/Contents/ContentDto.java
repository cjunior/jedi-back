package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ContentDto {
    private String title;
    private String subTitle;
    private String description;
    private String mainImage;
    private String mainImageText;
    private List<ContentItemUrlDto> items;

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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImageText() {
        return mainImageText;
    }

    public void setMainImageText(String mainImageText) {
        this.mainImageText = mainImageText;
    }

    public List<ContentItemUrlDto> getItems() {
        return items;
    }

    public void setItems(List<ContentItemUrlDto> items) {
        this.items = items;
    }

    public ContentDto() {
    }

    public ContentDto(String title, String subTitle, String description, String mainImage, String mainImageText, List<ContentItemUrlDto> items) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.mainImage = mainImage;
        this.mainImageText = mainImageText;
        this.items = items;
    }
}
