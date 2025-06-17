package com.ifce.jedi.dto.Contents;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ContentItemUpdateDto {
    private List<Long> itemsId;
    private List<MultipartFile> files;

    private List<String> imgsTexts;
    private List<Boolean> isMain;

    public List<Long> getItemsId() {
        return itemsId;
    }

    public void setItemsId(List<Long> itemsId) {
        this.itemsId = itemsId;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<String> getImgsTexts() {
        return imgsTexts;
    }

    public void setImgsTexts(List<String> imgsTexts) {
        this.imgsTexts = imgsTexts;
    }

    public List<Boolean> getIsMain() {
        return isMain;
    }

    public void setIsMain(List<Boolean> isMain) {
        this.isMain = isMain;
    }

    public ContentItemUpdateDto() {
    }

    public ContentItemUpdateDto(List<Long> itemsId, List<String> imgsTexts, List<MultipartFile> files, List<Boolean> isMain) {
        this.itemsId = itemsId;
        this.imgsTexts = imgsTexts;
        this.files = files;
        this.isMain = isMain;
    }
}
