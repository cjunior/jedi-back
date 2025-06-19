package com.ifce.jedi.model.SecoesSite.Rede;

import jakarta.persistence.*;

@Entity
public class RedeJediImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private RedeJediSection section;

    public RedeJediImage() {
    }

    public RedeJediImage(String url, String publicId) {
        this.url = url;
        this.publicId = publicId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getPublicId() { return publicId; }
    public void setPublicId(String publicId) { this.publicId = publicId; }

    public RedeJediSection getSection() { return section; }
    public void setSection(RedeJediSection section) { this.section = section; }
}
