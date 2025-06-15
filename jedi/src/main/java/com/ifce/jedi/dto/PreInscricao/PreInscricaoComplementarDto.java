package com.ifce.jedi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class PreInscricaoComplementarDto {

        @NotNull
        private LocalDate birthDate;

        @NotBlank
        private String municipality;

        @NotBlank
        private String cpf;

        @NotBlank
        private String rg;

        @NotNull
        private MultipartFile document;

        @NotNull
        private MultipartFile proofoOfAdress;

        // Construtor vazio (requerido pelo Spring)
        public PreInscricaoComplementarDto() {
        }

        // Getters e Setters
        public LocalDate getBirthDate() {
                return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
                this.birthDate = birthDate;
        }

        public String getMunicipality() {
                return municipality;
        }

        public void setMunicipality(String municipality) {
                this.municipality = municipality;
        }

        public String getCpf() {
                return cpf;
        }

        public void setCpf(String cpf) {
                this.cpf = cpf;
        }

        public String getRg() {
                return rg;
        }

        public void setRg(String rg) {
                this.rg = rg;
        }

        public MultipartFile getDocument() {
                return document;
        }

        public void setDocument(MultipartFile document) {
                this.document = document;
        }

        public MultipartFile getProofoOfAdress() {
                return proofoOfAdress;
        }

        public void setProofoOfAdress(MultipartFile proofoOfAdress) {
                this.proofoOfAdress = proofoOfAdress;
        }
}
