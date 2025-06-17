package com.ifce.jedi.dto.PresentationSection;

public record PresentationSectionUpdateDto(
        String title,
        String description,
        String firstStatistic,
        String secondStatistic
) {}