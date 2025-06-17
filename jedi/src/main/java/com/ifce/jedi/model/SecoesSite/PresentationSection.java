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
    private String description;
    private String firstStatistic;
    private String secondStatistic;
    private String imageUrl;
    private String cloudinaryPublicId;

    public PresentationSection() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFirstStatistic() { return firstStatistic; }
    public void setFirstStatistic(String firstStatistic) { this.firstStatistic = firstStatistic; }

    public String getSecondStatistic() { return secondStatistic; }
    public void setSecondStatistic(String secondStatistic) { this.secondStatistic = secondStatistic; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCloudinaryPublicId() { return cloudinaryPublicId; }
    public void setCloudinaryPublicId(String cloudinaryPublicId) { this.cloudinaryPublicId = cloudinaryPublicId; }

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