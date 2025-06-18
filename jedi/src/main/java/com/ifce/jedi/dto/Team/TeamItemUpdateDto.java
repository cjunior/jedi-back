package com.ifce.jedi.dto.Team;

import org.springframework.web.multipart.MultipartFile;

public class TeamItemUpdateDto {
    private Long id;
    private MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public TeamItemUpdateDto() {
    }

    public TeamItemUpdateDto(Long id, MultipartFile file) {
        this.id = id;
        this.file = file;
    }
}
