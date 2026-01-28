package com.ifce.jedi.model.ResultadoProcesso;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "resultado_processo")
public class ResultadoProcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "nome_processo", nullable = false)
    private String nomeProcesso;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "deferidos_url")
    private String deferidosUrl;

    @Column(name = "deferidos_file_name")
    private String deferidosFileName;

    @Column(name = "indeferidos_url")
    private String indeferidosUrl;

    @Column(name = "indeferidos_file_name")
    private String indeferidosFileName;

    @Column(name = "lista_espera_url")
    private String listaEsperaUrl;

    @Column(name = "lista_espera_file_name")
    private String listaEsperaFileName;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getDeferidosUrl() {
        return deferidosUrl;
    }

    public void setDeferidosUrl(String deferidosUrl) {
        this.deferidosUrl = deferidosUrl;
    }

    public String getDeferidosFileName() {
        return deferidosFileName;
    }

    public void setDeferidosFileName(String deferidosFileName) {
        this.deferidosFileName = deferidosFileName;
    }

    public String getIndeferidosUrl() {
        return indeferidosUrl;
    }

    public void setIndeferidosUrl(String indeferidosUrl) {
        this.indeferidosUrl = indeferidosUrl;
    }

    public String getIndeferidosFileName() {
        return indeferidosFileName;
    }

    public void setIndeferidosFileName(String indeferidosFileName) {
        this.indeferidosFileName = indeferidosFileName;
    }

    public String getListaEsperaUrl() {
        return listaEsperaUrl;
    }

    public void setListaEsperaUrl(String listaEsperaUrl) {
        this.listaEsperaUrl = listaEsperaUrl;
    }

    public String getListaEsperaFileName() {
        return listaEsperaFileName;
    }

    public void setListaEsperaFileName(String listaEsperaFileName) {
        this.listaEsperaFileName = listaEsperaFileName;
    }
}
