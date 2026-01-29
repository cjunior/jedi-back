package com.ifce.jedi.dto.Arquivos;

public record PastaResponseDto(
        Long id,
        String nome,
        String descricao,
        String slug,
        Long parentId
) {
}
