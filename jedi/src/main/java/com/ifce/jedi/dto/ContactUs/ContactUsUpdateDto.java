package com.ifce.jedi.dto.ContactUs;

public class ContactUsUpdateDto {
    private String title;
    private String subTitle;
    private String description;

    public ContactUsUpdateDto(String title, String subTitle, String description) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
    }

    public ContactUsUpdateDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
