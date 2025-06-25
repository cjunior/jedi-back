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

    private LocalDate birthDate;

    private String municipality;

    private String cpf;

    private String rg;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "proof_of_adress_url")
    private String proofOfAdressUrl;

    @Column(name = "continuation_token", unique = true)
    private String continuationToken;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    @Enumerated(EnumType.STRING)
    private StatusPreInscricao status = StatusPreInscricao.INCOMPLETO;

    public PreInscricao() {

    }

    public PreInscricao(String completeName, String email, String cellPhone) {
        this.completeName = completeName;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getProofOfAdressUrl() {
        return proofOfAdressUrl;
    }

    public void setProofOfAdressUrl(String proofOfAdressUrl) {
        this.proofOfAdressUrl = proofOfAdressUrl;
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

    public String getContinuationToken() {
        return continuationToken;
    }

    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }

    public StatusPreInscricao getStatus() {
        return status;
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

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public void setTokenExpiration(LocalDateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public void setStatus(StatusPreInscricao status) {
        this.status = status;
    }
}
