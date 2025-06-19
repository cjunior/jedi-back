package com.ifce.jedi.model.SecoesSite.Rede;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class RedeJediSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RedeJediImage> imagens;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<RedeJediImage> getImagens() { return imagens; }
    public void setImagens(List<RedeJediImage> imagens) { this.imagens = imagens; }
}