package com.ifce.jedi.dto.Blog;

import java.util.List;

public record BlogSectionResponseDto(
        Long id,
        String title,
        List<BlogItemResponseDto> items
) {}