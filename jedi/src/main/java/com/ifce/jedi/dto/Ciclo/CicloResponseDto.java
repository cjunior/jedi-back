package com.ifce.jedi.dto.Ciclo;

import com.ifce.jedi.model.Ciclo.Ciclo;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CicloResponseDto(
        UUID id,
        String nome,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Set<String> municipios
) {

    public static CicloResponseDto fromEntity(Ciclo ciclo) {
        return new CicloResponseDto(
                ciclo.getId(),
                ciclo.getNomeCiclo(),
                ciclo.getDataInicio(),
                ciclo.getDataFim(),
                ciclo.getMunicipios()
        );
    }
}
