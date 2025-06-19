package com.ifce.jedi.dto.PresentationSection;

public record PresentationSectionUpdateDto(
        String title,
        String firstDescription,
        String secondDescription,
        String firstStatistic,
        String secondStatistic,
        String imgDescription
) {}