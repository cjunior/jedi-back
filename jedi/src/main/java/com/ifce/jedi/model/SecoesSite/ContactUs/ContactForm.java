package com.ifce.jedi.model.SecoesSite.ContactUs;


import jakarta.persistence.*;

@Entity
@Table(name = "contact_form")
public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String city;
    private String subject;
    private String message;

    @OneToOne
    @JoinColumn(name = "contact_us_id")
    private ContactUs contactUs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContactForm() {
    }

    public ContactForm(Long id, String name, String email, String city, String subject, String message, ContactUs contactUs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.subject = subject;
        this.message = message;
        this.contactUs = contactUs;
    }

    public ContactUs getContactUs() {
        return contactUs;
    }

    public void setContactUs(ContactUs contactUs) {
        this.contactUs = contactUs;
    }

}
