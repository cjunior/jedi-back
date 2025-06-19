package com.ifce.jedi.dto.ContactUs;

public class ContactFormEmailDto {
    private String name;
    private String email;
    private String city;
    private String subject;
    private String message;

    public ContactFormEmailDto() {
    }

    public ContactFormEmailDto(String name, String email, String city, String subject, String message) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.subject = subject;
        this.message = message;
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
}
