package com.ifce.jedi.dto.Ciclo;

import java.time.LocalDateTime;
import java.util.Set;

public record CicloRequestDto(
        String nome,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Set<String> municipios
) {
}
