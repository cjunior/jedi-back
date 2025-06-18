package com.ifce.jedi.dto.Blog;

import org.springframework.web.multipart.MultipartFile;

public record BlogItemImageUploadDto(
        MultipartFile file
) {}