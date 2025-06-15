package com.ifce.jedi.model.SecoesSite;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "presentation_section")
public class PresentationSection {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String descricao;
    private String itemAzul;
    private String itemVerde;
    private String imageUrl;
    private String texto;

    public PresentationSection() {
    }

    public PresentationSection(UUID id, String descricao, String itemAzul, String itemVerde, String imageUrl, String texto) {
        this.id = id;
        this.descricao = descricao;
        this.itemAzul = itemAzul;
        this.itemVerde = itemVerde;
        this.imageUrl = imageUrl;
        this.texto = texto;
    }

    public UUID getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getItemAzul() {
        return itemAzul;
    }

    public void setItemAzul(String itemAzul) {
        this.itemAzul = itemAzul;
    }

    public String getItemVerde() {
        return itemVerde;
    }

    public void setItemVerde(String itemVerde) {
        this.itemVerde = itemVerde;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
