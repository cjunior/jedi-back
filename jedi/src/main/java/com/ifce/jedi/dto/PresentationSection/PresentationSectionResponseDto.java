package com.ifce.jedi.dto.PresentationSection;

public record PresentationSectionResponseDto(
        Long id,
        String title,
        String firstDescription,
        String secondDescription,
        String firstStatistic,
        String secondStatistic,
        String imgUrl,
        String imgDescription
) {}