package com.ifce.jedi.dto.PreInscricao;

import com.ifce.jedi.model.User.PreInscricao;

import java.time.LocalDate;

public record PreInscricaoDadosDto(
        String completeName,
        String email,
        String cellPhone,
        LocalDate birthDate,
        String municipality,
        String cpf,
        String rg,
        String documentUrl,
        String proofOfAdressUrl
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
                pre.getDocumentUrl(),
                pre.getProofOfAdressUrl()
        );
    }
}
