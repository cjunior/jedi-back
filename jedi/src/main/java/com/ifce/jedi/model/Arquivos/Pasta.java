package com.ifce.jedi.model.Arquivos;

import jakarta.persistence.*;

@Entity
@Table(
        name = "pastas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"parent_id", "slug"})
        }
)
public class Pasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private String slug;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Pasta parent;

    public Pasta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Pasta getParent() {
        return parent;
    }

    public void setParent(Pasta parent) {
        this.parent = parent;
    }
}
