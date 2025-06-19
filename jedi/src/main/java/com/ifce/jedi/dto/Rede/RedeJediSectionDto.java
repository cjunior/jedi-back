package com.ifce.jedi.dto.Rede;

import java.util.List;

public class RedeJediSectionDto {
    private Long id;
    private String titulo;
    private List<RedeJediImageDto> imagens;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<RedeJediImageDto> getImagens() { return imagens; }
    public void setImagens(List<RedeJediImageDto> imagens) { this.imagens = imagens; }
}
