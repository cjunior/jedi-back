package com.ifce.jedi.model.SecoesSite.Team;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TeamItem> getItems() {
        return items;
    }

    public void setItems(List<TeamItem> items) {
        this.items = items;
    }

    public Team() {
    }

    public Team(Long id, String title, List<TeamItem> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }
}
