package com.ifce.jedi.dto.Blog;

public record BlogItemUpdateDto(
        String title,
        String author,
        String date,
        String readingTime
) {}