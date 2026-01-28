package com.ifce.jedi.dto.ResultadoProcesso;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class ResultadoProcessoCreateDto {
    private String titulo;
    private String nomeProcesso;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataPublicacao;

    private MultipartFile deferidosPdf;
    private MultipartFile indeferidosPdf;
    private MultipartFile listaEsperaPdf;

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

    public MultipartFile getDeferidosPdf() {
        return deferidosPdf;
    }

    public void setDeferidosPdf(MultipartFile deferidosPdf) {
        this.deferidosPdf = deferidosPdf;
    }

    public MultipartFile getIndeferidosPdf() {
        return indeferidosPdf;
    }

    public void setIndeferidosPdf(MultipartFile indeferidosPdf) {
        this.indeferidosPdf = indeferidosPdf;
    }

    public MultipartFile getListaEsperaPdf() {
        return listaEsperaPdf;
    }

    public void setListaEsperaPdf(MultipartFile listaEsperaPdf) {
        this.listaEsperaPdf = listaEsperaPdf;
    }
}
