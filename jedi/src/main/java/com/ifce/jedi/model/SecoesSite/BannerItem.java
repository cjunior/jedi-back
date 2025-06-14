package com.ifce.jedi.model.SecoesSite;

import jakarta.persistence.*;

@Entity
@Table(name = "banner_items")
public class BannerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;
    private String buttonText;
    private String buttonUrl;
    private String cloudinaryPublicId;


    @ManyToOne
    @JoinColumn(name = "banner_id")
    private Banner banner;

    public BannerItem(Long id, String imgUrl, String buttonText, String buttonUrl, Banner banner) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.buttonText = buttonText;
        this.buttonUrl = buttonUrl;
        this.banner = banner;
    }

    public BannerItem() {
    }

    public Long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
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
