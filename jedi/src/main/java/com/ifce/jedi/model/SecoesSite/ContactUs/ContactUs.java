package com.ifce.jedi.model.SecoesSite.ContactUs;

import jakarta.persistence.*;

@Entity
@Table(name = "ContactUs")
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String subTitle;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public ContactUs() {
    }

    public ContactUs(Long id, String title, String description, String subTitle, ContactForm contactForm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subTitle = subTitle;
        this.contactForm = contactForm;
    }

    @OneToOne
    @JoinColumn(name = "contact_form_id")
    private ContactForm contactForm;

    public ContactForm getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }

}
