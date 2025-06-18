package com.ifce.jedi.model.SecoesSite.Contents;

import com.ifce.jedi.model.SecoesSite.BaseSectionItem;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Content_item")
public class ContentItem extends BaseSectionItem {
    private String imgText;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public ContentItem() {
    }

    public ContentItem(String imgText) {
        this.imgText = imgText;
    }
}
