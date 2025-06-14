package com.ifce.jedi.model.SecoesSite;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "banner_item")
public class BannerItem extends BaseSectionItem{
    private String buttonText;
    private String buttonUrl;

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
