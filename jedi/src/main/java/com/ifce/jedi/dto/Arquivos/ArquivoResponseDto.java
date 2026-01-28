package com.ifce.jedi.dto.Arquivos;

import java.time.LocalDateTime;

public record ArquivoResponseDto(
        Long id,
        String nome,
        String url,
        LocalDateTime uploadedAt
) {
}
