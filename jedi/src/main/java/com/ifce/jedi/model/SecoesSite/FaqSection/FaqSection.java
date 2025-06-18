package com.ifce.jedi.model.SecoesSite.FaqSection;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "faq_sections")
public class FaqSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;

    @OneToMany(mappedBy = "faqSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaqItem> items;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public List<FaqItem> getItems() { return items; }
    public void setItems(List<FaqItem> items) { this.items = items; }
}