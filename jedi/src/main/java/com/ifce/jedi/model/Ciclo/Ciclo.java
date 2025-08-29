package com.ifce.jedi.model.Ciclo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
public class Ciclo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nomeCiclo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @ElementCollection
    @CollectionTable(name = "ciclo_municipios", joinColumns = @JoinColumn(name = "ciclo_id"))
    private Set<String> municipios;

    public UUID getId() {
        return id;
    }

    public String getNomeCiclo() {
        return nomeCiclo;
    }

    public void setNomeCiclo(String nomeCiclo) {
        this.nomeCiclo = nomeCiclo;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Set<String> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(Set<String> municipios) {
        this.municipios = municipios;
    }
}
