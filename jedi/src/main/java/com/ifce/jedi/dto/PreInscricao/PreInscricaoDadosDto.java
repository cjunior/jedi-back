package com.ifce.jedi.dto.PreInscricao;

import com.ifce.jedi.model.User.PreInscricao;

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
        String documentBase64 = null;
        String proofOfAdressBase64 = null;

        if (pre.getDocument() != null && pre.getProofOfAdress() != null) {
            documentBase64 = Base64.getEncoder().encodeToString(pre.getDocument());
            proofOfAdressBase64 = Base64.getEncoder().encodeToString(pre.getProofOfAdress());
        }

        return new PreInscricaoDadosDto(
                pre.getCompleteName(),
                pre.getEmail(),
                pre.getCellPhone(),
                pre.getBirthDate(),
                pre.getMunicipality(),
                pre.getCpf(),
                pre.getRg(),
                documentBase64,
                proofOfAdressBase64
        );
    }

}

