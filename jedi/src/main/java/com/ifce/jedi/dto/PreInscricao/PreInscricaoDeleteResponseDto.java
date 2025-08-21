package com.ifce.jedi.dto.PreInscricao;

import java.util.UUID;

public record PreInscricaoDeleteResponseDto(
        UUID id,
        String message
) {

    public static PreInscricaoDeleteResponseDto from(UUID id) {
        return new PreInscricaoDeleteResponseDto(id, "Pré-inscrição excluída com sucesso.");
    }
}
