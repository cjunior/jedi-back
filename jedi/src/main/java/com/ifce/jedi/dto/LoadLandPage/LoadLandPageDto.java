package com.ifce.jedi.dto.LoadLandPage;

import com.ifce.jedi.dto.Banner.BannerResponseDto;
import com.ifce.jedi.dto.ContactUs.ContactUsResponseDto;
import com.ifce.jedi.dto.Contents.ContentResponseDto;
import com.ifce.jedi.dto.FaqSection.FaqSectionResponseDto;
import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionResponseDto;
import com.ifce.jedi.dto.Team.TeamResponseDto;

public record LoadLandPageDto(
        HeaderResponseDto headerResponseDto,
        BannerResponseDto bannerResponseDto,
        PresentationSectionResponseDto presentationSectionResponseDto,
        TeamResponseDto teamResponseDto,
        ContentResponseDto contentResponseDto,
        FaqSectionResponseDto faqSectionResponseDto,
        ContactUsResponseDto contactUsResponseDto
) {}