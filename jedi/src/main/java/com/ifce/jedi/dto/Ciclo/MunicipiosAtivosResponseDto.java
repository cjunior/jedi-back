package com.ifce.jedi.dto.Ciclo;

import java.time.LocalDateTime;
import java.util.Set;

public record MunicipiosAtivosResponseDto(
        LocalDateTime dataConsulta,
        Set<String> municipios
) {

    public static MunicipiosAtivosResponseDto of(Set<String> municipios) {
        return new MunicipiosAtivosResponseDto(LocalDateTime.now(), municipios);
    }
}
