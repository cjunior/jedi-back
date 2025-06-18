package com.ifce.jedi.dto.FaqSection;

public record FaqItemDto(
        Long id,
        String question,
        String answer,
        Integer position
) {}