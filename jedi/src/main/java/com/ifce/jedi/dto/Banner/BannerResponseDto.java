package com.ifce.jedi.dto.Banner;

import java.util.List;

public record BannerResponseDto(Long id, String title, String description, List<BannerItemResponseDto> items) {
}
