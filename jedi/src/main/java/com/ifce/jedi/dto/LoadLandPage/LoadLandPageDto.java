package com.ifce.jedi.dto.LoadLandPage;

import com.ifce.jedi.dto.Banner.BannerResponseDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionResponseDto;
import com.ifce.jedi.dto.Team.TeamResponseDto;

public record LoadLandPageDto(HeaderResponseDto headerResponseDto, BannerResponseDto bannerResponseDto, TeamResponseDto teamResponseDto, PresentationSectionResponseDto presentationSectionResponseDto) {
}
