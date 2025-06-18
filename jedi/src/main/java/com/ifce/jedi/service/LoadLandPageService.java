package com.ifce.jedi.service;

import com.ifce.jedi.dto.Header.HeaderResponseDto;
import com.ifce.jedi.dto.LoadLandPage.LoadLandPageDto;
import com.ifce.jedi.dto.LoadLandPage.UpdateLoadLandPageDto;
import com.ifce.jedi.model.SecoesSite.Header.Header;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class LoadLandPageService {
    @Autowired
    private HeaderService headerService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PresentationSectionService presentationSectionService;
    @Autowired
    private FaqSectionService faqSectionService;


    public LoadLandPageDto getAll() {
        var header = headerService.getHeader();
        var banner = bannerService.getBanner();
        var team = teamService.getTeam();
        var presentationSection = presentationSectionService.get();
        var faqSection = faqSectionService.get();
        LoadLandPageDto dto = new LoadLandPageDto(header, banner, team, presentationSection, faqSection);

        return dto;
    }
}

