package com.ifce.jedi.dto.Contents;

public class ContentItemUrlDto {
    private String imgUrl;
    private String imgText;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public ContentItemUrlDto() {
    }

    public ContentItemUrlDto(String imgUrl, String imgText) {
        this.imgUrl = imgUrl;
        this.imgText = imgText;
    }
}
