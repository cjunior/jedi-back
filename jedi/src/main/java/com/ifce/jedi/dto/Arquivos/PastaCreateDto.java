package com.ifce.jedi.dto.Arquivos;

public class PastaCreateDto {

    private String nome;
    private Long parentId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
