package com.ifce.jedi.dto.Banner;


import java.util.List;

public record BannerDto(String title, String description, List<BannerItemDto> items) {
}
