package com.ifce.jedi.model.SecoesSite.Contents;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "content_section")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subTitle;
    private String description;
    private String mainImageUrl;
    private String mainImageText;

    private String fileName;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentItem> imgCarousel = new ArrayList<>();

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

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getMainImageText() {
        return mainImageText;
    }

    public void setMainImageText(String mainImageText) {
        this.mainImageText = mainImageText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ContentItem> getImgCarousel() {
        return imgCarousel;
    }

    public void setImgCarousel(List<ContentItem> imgCarousel) {
        this.imgCarousel = imgCarousel;
    }

    public Content() {
    }

    public Content(Long id, String title, String subTitle, String description, String mainImageUrl, String mainImageText, String fileName, List<ContentItem> imgCarousel) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.mainImageText = mainImageText;
        this.fileName = fileName;
        this.imgCarousel = imgCarousel;
    }
}
