package com.ifce.jedi.model.SecoesSite.Contents;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "content_section")
public class Content {
    @Id
    private Long id;
    private String title;
    private String description;
    private String mainImageUrl;
    List<ContentItem> imgCarousel;

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

    public List<ContentItem> getImgCarousel() {
        return imgCarousel;
    }

    public void setImgCarousel(List<ContentItem> imgCarousel) {
        this.imgCarousel = imgCarousel;
    }

    public Content() {
    }

    public Content(Long id, String title, String description, String mainImageUrl, List<ContentItem> imgCarousel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.imgCarousel = imgCarousel;
    }
}
