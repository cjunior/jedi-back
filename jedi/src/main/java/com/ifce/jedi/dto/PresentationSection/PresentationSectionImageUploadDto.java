package com.ifce.jedi.dto.PresentationSection;

import org.springframework.web.multipart.MultipartFile;

public class PresentationSectionImageUploadDto {
    private MultipartFile file;

    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }
}