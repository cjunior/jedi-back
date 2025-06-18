package com.ifce.jedi.model.SecoesSite.BlogSection;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "blog_sections")
public class BlogSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "blogSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogItem> items;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<BlogItem> getItems() { return items; }
    public void setItems(List<BlogItem> items) { this.items = items; }
}

