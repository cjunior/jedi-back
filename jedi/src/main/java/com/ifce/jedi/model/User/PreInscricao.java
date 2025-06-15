package com.ifce.jedi.model.User;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDate birthDate;

    private String municipality;

    private String cpf;

    private String rg;

    @Lob
    @Column(name = "document")
    private byte[] document;

    @Lob
    @Column(name =  "proof_of_adress")
    private byte[] proofoOfAdressUrl;

    @Column(name = "continuation_token", unique = true)
    private String continuationToken;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    public PreInscricao() {

    }

    public PreInscricao(String completeName, String email, String cellPhone) {
        this.completeName = completeName;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public UUID getId() {
        return id;
    }

    public String getCompleteName() {
        return completeName;
    }

    public String getEmail() {
        return email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public UserRole getRole() {
        return role;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public byte[] getDocument() {
        return document;
    }

    public byte[] getProofoOfAdressUrl() {
        return proofoOfAdressUrl;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public void setProofoOfAdressUrl(byte[] proofoOfAdressUrl) {
        this.proofoOfAdressUrl = proofoOfAdressUrl;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public void setTokenExpiration(LocalDateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
}
