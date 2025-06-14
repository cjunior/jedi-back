package com.ifce.jedi.model.SecoesSite;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseSectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;
    private String cloudinaryPublicId;
    @ManyToOne
    @JoinColumn(name = "banner_id")
    private Banner banner;

    public Long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getCloudinaryPublicId() {
        return cloudinaryPublicId;
    }

    public void setCloudinaryPublicId(String publicId) {
        this.cloudinaryPublicId = publicId;
    }
}
