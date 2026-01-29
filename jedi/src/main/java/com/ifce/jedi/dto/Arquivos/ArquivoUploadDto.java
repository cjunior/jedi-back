package com.ifce.jedi.dto.Arquivos;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ArquivoUploadDto {

    private MultipartFile[] files;
    private List<String> nomes;

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public List<String> getNomes() {
        return nomes;
    }

    public void setNomes(List<String> nomes) {
        this.nomes = nomes;
    }
}
