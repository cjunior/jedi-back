package com.ifce.jedi.dto.LoadLandPage;

import org.springframework.web.multipart.MultipartFile;

public class blogitemsWrapperDto {
    private Long id;
    private String title;
    private String author;
    private String date;
    private String readingTime;
    private String imageDescription;

    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public blogitemsWrapperDto() {
    }

    public blogitemsWrapperDto(Long id, String title, String author, String date, String readingTime, String imageDescription, MultipartFile file) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.readingTime = readingTime;
        this.imageDescription = imageDescription;
        this.file = file;
    }
}
