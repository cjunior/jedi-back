package com.ifce.jedi.dto.Rede;

import org.springframework.web.multipart.MultipartFile;

public class imagemRedeJedUpdateWrapperDto {

    private Long id;
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public imagemRedeJedUpdateWrapperDto() {
    }

    public imagemRedeJedUpdateWrapperDto(Long id, MultipartFile file) {
        this.id = id;
        this.file = file;
    }
}
