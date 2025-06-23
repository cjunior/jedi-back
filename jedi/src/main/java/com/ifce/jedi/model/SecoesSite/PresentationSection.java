package com.ifce.jedi.model.SecoesSite;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "presentation_sections")
public class PresentationSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String firstDescription;
    private String secondDescription;
    private String firstStatistic;
    private String secondStatistic;
    private String imgUrl;
    private String imgDescription;
    private String fileName;

    public PresentationSection() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFirstDescription() { return firstDescription; }
    public void setFirstDescription(String firstDescription) { this.firstDescription = firstDescription; }

    public String getSecondDescription() { return secondDescription; }
    public void setSecondDescription(String secondDescription) { this.secondDescription = secondDescription; }

    public String getFirstStatistic() { return firstStatistic; }
    public void setFirstStatistic(String firstStatistic) { this.firstStatistic = firstStatistic; }

    public String getSecondStatistic() { return secondStatistic; }
    public void setSecondStatistic(String secondStatistic) { this.secondStatistic = secondStatistic; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getImgDescription() { return imgDescription; }
    public void setImgDescription(String imgDescription) { this.imgDescription = imgDescription; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PresentationSection that = (PresentationSection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}