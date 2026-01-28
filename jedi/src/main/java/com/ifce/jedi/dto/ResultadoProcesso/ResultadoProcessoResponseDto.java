package com.ifce.jedi.dto.ResultadoProcesso;

import java.time.LocalDate;

public record ResultadoProcessoResponseDto(
        Long id,
        String titulo,
        String nomeProcesso,
        LocalDate dataPublicacao,
        String deferidosUrl,
        String indeferidosUrl,
        String listaEsperaUrl
) {
}
