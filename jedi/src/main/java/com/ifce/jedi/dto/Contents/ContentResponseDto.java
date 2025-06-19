package com.ifce.jedi.dto.Contents;

import com.ifce.jedi.model.SecoesSite.Contents.Content;
import com.ifce.jedi.model.SecoesSite.Header.Header;

import java.util.List;

public record ContentResponseDto(Long id, String title, String subTitle, String description, String mainImg, List<ContentItemResponseDto> items) {
}