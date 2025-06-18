package com.ifce.jedi.dto.FaqSection;

import java.util.List;

public record FaqSectionResponseDto(
        Long id,
        String title,
        String subtitle,
        List<FaqItemResponseDto> items
) {}