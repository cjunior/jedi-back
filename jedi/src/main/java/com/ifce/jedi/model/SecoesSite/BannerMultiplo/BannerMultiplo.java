package com.ifce.jedi.model.SecoesSite.BannerMultiplo;

import com.ifce.jedi.model.SecoesSite.BaseSectionItem;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "banner_multiplo")
public class BannerMultiplo extends BaseSectionItem {
    private String title;
    private String linkUrl;
    private Integer position;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
