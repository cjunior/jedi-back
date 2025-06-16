package com.ifce.jedi.model.SecoesSite.Banner;

import com.ifce.jedi.model.SecoesSite.BaseSectionItem;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "banner_item")
public class BannerItem extends BaseSectionItem {
    private String buttonText;
    private String buttonUrl;
    @ManyToOne
    @JoinColumn(name = "banner_id")
    private Banner banner;

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
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
}
