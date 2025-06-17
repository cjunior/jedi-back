package com.ifce.jedi.dto.PresentationSection;

import com.ifce.jedi.model.SecoesSite.PresentationSection;

public record PresentationSectionDto(
        String title,
        String description,
        String firstStatistic,
        String secondStatistic,
        String imageUrl
) {
    public PresentationSectionDto(PresentationSection entity) {
        this(
                entity.getTitle(),
                entity.getDescription(),
                entity.getFirstStatistic(),
                entity.getSecondStatistic(),
                entity.getImageUrl()
        );
    }
}