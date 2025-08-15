package com.ifce.jedi.dto.PreInscricao;

import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.model.User.StatusPreInscricao;

import java.time.LocalDate;

public record PreInscricaoDadosDto(
        String completeName,
        String email,
        String cellPhone,
        LocalDate birthDate,
        String municipality,
        String otherMunicipality,
        String cpf,
        String rg,
        String documentUrl,
        String proofOfAdressUrl,
        StatusPreInscricao status,
        Boolean acceptedTerms
) {
    public static PreInscricaoDadosDto fromEntity(PreInscricao pre) {
        return new PreInscricaoDadosDto(
                pre.getCompleteName(),
                pre.getEmail(),
                pre.getCellPhone(),
                pre.getBirthDate(),
                pre.getMunicipality(),
                pre.getOtherMunicipality(),
                pre.getCpf(),
                pre.getRg(),
                pre.getDocumentUrl(),
                pre.getProofOfAdressUrl(),
                pre.getStatus(),
                pre.getAcceptedTerms()
        );
    }
}
