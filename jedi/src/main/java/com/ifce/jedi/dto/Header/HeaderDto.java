package com.ifce.jedi.dto.Header;


import org.springframework.web.multipart.MultipartFile;

public class HeaderDto {
    private MultipartFile file;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String buttonText;

    public HeaderDto() {
    }

    public HeaderDto(MultipartFile file, String text1, String text2, String text3, String text4, String buttonText) {
        this.file = file;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.buttonText = buttonText;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
