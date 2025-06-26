package com.ifce.jedi.dto.Blog;

import org.springframework.web.multipart.MultipartFile;

public record BlogItemCreateDto(
        String title,
        String author,
        String date,
        String readingTime,
        String imageDescription,
        String description,          // Novo campo
        MultipartFile file,          // Imagem principal
        MultipartFile iconFile       // Novo campo (ícone)
) {}