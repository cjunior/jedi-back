package com.ifce.jedi.model.SecoesSite.BlogSection;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "blog_items")
public class BlogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String date;
    private String readingTime;
    private String imageUrl;
    private String imageDescription;
    private String cloudinaryPublicId;
    private String iconUrl;              // Novo campo
    private String iconCloudinaryPublicId; // ID no Cloudinary (para exclusão)
    private String description;          // Novo campo (texto longo)

    @ManyToOne
    @JoinColumn(name = "blog_section_id")
    private BlogSection blogSection;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getReadingTime() { return readingTime; }
    public void setReadingTime(String readingTime) { this.readingTime = readingTime; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getImageDescription() { return imageDescription; }
    public void setImageDescription(String imageDescription) { this.imageDescription = imageDescription; }
    public String getCloudinaryPublicId() { return cloudinaryPublicId; }
    public void setCloudinaryPublicId(String cloudinaryPublicId) { this.cloudinaryPublicId = cloudinaryPublicId; }
    public BlogSection getBlogSection() { return blogSection; }
    public void setBlogSection(BlogSection blogSection) { this.blogSection = blogSection; }
    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public String getIconCloudinaryPublicId() { return iconCloudinaryPublicId; }
    public void setIconCloudinaryPublicId(String iconCloudinaryPublicId) { this.iconCloudinaryPublicId = iconCloudinaryPublicId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}