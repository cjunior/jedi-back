package com.ifce.jedi.dto.ContactUs;

public class ContactUsUpdateDto {
    private Long id;
    private String title;
    private String subTitle;
    private String description;

    public ContactUsUpdateDto(Long id, String title, String subTitle, String description) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
    }

    public ContactUsUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
