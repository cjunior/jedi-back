package com.ifce.jedi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pre_inscricao")
public class PreInscricao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String completeName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String cellPhone;

    private LocalDate birthDate;

    private String municipality;

    private String cpf;

    private String rg;



}
