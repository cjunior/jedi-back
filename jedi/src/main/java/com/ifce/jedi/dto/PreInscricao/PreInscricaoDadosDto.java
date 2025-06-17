package com.ifce.jedi.dto.PreInscricao;

import com.ifce.jedi.model.User.PreInscricao;

import java.time.LocalDate;

import java.time.LocalDate;
import java.util.Base64;

public record PreInscricaoDadosDto(
        String completeName,
        String email,
        String cellPhone,
        LocalDate birthDate,
        String municipality,
        String cpf,
        String rg,
        String documentBase64,
        String proofOfAdressBase64
) {
    public static PreInscricaoDadosDto fromEntity(PreInscricao pre) {
        return new PreInscricaoDadosDto(
                pre.getCompleteName(),
                pre.getEmail(),
                pre.getCellPhone(),
                pre.getBirthDate(),
                pre.getMunicipality(),
                pre.getCpf(),
                pre.getRg(),
                pre.getDocument() != null ? Base64.getEncoder().encodeToString(pre.getDocument()) : null,
                pre.getProofOfAdress() != null ? Base64.getEncoder().encodeToString(pre.getProofOfAdress()) : null
        );
    }
}

