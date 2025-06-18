package com.ifce.jedi.dto.FaqSection;

public record FaqItemResponseDto(
        Long id,
        String question,
        String answer,
        Integer position
) {}