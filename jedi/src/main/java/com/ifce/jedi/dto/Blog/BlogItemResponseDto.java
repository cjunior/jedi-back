package com.ifce.jedi.dto.Blog;

public record BlogItemResponseDto(
        Long id,
        String title,
        String author,
        String date,
        String readingTime,
        String imageUrl,
        String imageDescription,
        String iconUrl,              // Novo campo
        String description           // Novo campo
) {}