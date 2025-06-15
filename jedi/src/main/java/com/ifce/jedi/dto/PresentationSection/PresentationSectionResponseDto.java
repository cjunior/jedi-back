package com.ifce.jedi.dto.PresentationSection;

import com.ifce.jedi.model.SecoesSite.PresentationSection;

import java.util.UUID;

public record PresentationSectionResponseDto(
        UUID id,
        String descricao,
        String itemAzul,
        String itemVerde,
        String imageUrl,
        String texto
) {
    public PresentationSectionResponseDto(PresentationSection presentationSection) {
        this(
                presentationSection.getId(),
                presentationSection.getDescricao(),
                presentationSection.getItemAzul(),
                presentationSection.getItemVerde(),
                presentationSection.getImageUrl(),
                presentationSection.getTexto()
        );
    }
}
