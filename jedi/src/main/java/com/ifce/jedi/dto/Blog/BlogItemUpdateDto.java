package com.ifce.jedi.dto.Blog;

import org.springframework.web.multipart.MultipartFile;

public record BlogItemUpdateDto(
        String title,
        String author,
        String date,
        String readingTime,
        String imageDescription,
        MultipartFile file
) {}