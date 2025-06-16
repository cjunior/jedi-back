package com.ifce.jedi.dto.Team;

import com.ifce.jedi.dto.Banner.BannerItemResponseDto;

import java.util.List;

public record TeamResponseDto(Long id, String title, List<TeamItemResponseDto> items) {
}
