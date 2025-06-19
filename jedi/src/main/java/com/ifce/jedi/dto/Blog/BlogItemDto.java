package com.ifce.jedi.dto.Blog;

public record BlogItemDto(
        String title,
        String author,
        String date,
        String readingTime,
        String imageUrl,
        String imageDescription
) {}