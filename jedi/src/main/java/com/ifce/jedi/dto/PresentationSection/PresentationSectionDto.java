package com.ifce.jedi.dto.PresentationSection;

import com.ifce.jedi.model.SecoesSite.PresentationSection;

public record PresentationSectionDto(
        String title,
        String firstDescription,
        String secondDescription,
        String firstStatistic,
        String secondStatistic,
        String imgUrl,
        String imgDescription
) {
    public PresentationSectionDto(PresentationSection entity) {
        this(
                entity.getTitle(),
                entity.getFirstDescription(),
                entity.getSecondDescription(),
                entity.getFirstStatistic(),
                entity.getSecondStatistic(),
                entity.getImgUrl(),
                entity.getImgDescription()
        );
    }
}