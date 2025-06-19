package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.*;
import com.ifce.jedi.dto.ContactUs.ContactUsUpdateDto;
import com.ifce.jedi.dto.Contents.ContentItemDto;
import com.ifce.jedi.dto.Contents.UpdateContentDto;
import com.ifce.jedi.dto.FaqSection.FaqItemUpdateDto;
import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.LoadLandPage.ContentItemUpdateLandPageDto;
import com.ifce.jedi.dto.LoadLandPage.FaqItemUpdateLandPageDto;
import com.ifce.jedi.dto.LoadLandPage.LoadLandPageDto;
import com.ifce.jedi.dto.LoadLandPage.UpdateLoadLandPageDto;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionUpdateDto;
import com.ifce.jedi.dto.Team.TeamItemDto;
import com.ifce.jedi.dto.Team.TeamItemUpdateDto;
import com.ifce.jedi.dto.Team.TeamUpdateDto;
import com.ifce.jedi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loadlandpage")
public class LoadLandPageController {
    @Autowired
    LoadLandPageService loadLandPageService;
    @Autowired
    private BannerService bannerService;

    @Autowired
    private HeaderService headerService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PresentationSectionService presentationService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private FaqSectionService faqSectionService;

    @GetMapping("/get")
    public ResponseEntity<LoadLandPageDto> getHeader() {
        LoadLandPageDto landPage = loadLandPageService.getAll();
        return landPage == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(landPage);
    }

    @PutMapping(value = "/update-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAll(@ModelAttribute UpdateLoadLandPageDto dto) throws IOException {

        // Atualiza itens do Banner (já corrigido anteriormente)
        if (dto.getBannerItems() != null && !dto.getBannerItems().isEmpty()) {
            for (BannerItemUpdate itemUpdate : dto.getBannerItems()) {
                BannerItemDto itemDto = new BannerItemDto(
                        itemUpdate.getButtonText(),
                        itemUpdate.getButtonUrl() != null ? itemUpdate.getButtonUrl() : ""
                );

                MultipartFile file = itemUpdate.getFile();
                bannerService.updateSlide(itemUpdate.getId(), file, itemDto);
            }
        }

        // Atualiza Header
        var headerDto = new HeaderDto(
                dto.getHeaderFile(),
                dto.getHeaderText1(),
                dto.getHeaderText2(),
                dto.getHeaderText3(),
                dto.getHeaderText4(),
                dto.getHeaderButtonText()
        );
        headerService.updateHeader(headerDto);

        var presentationSectionUpdateDto = new PresentationSectionUpdateDto(
                dto.getPresentationSectionTitle(),
                dto.getPresentationSectionFirstDescription(),
                dto.getPresentationSectionSecondDescription(),
                dto.getPresentationSectionFirstStatistic(),
                dto.getPresentationSectionSecondStatistic(),
                dto.getPresentationSectionImgDescription()
        );
        presentationService.update(presentationSectionUpdateDto);
        presentationService.updateImage(dto.getPresentationSectionFile());

        // Atualiza título da Equipe
        var teamDto = new TeamUpdateDto(dto.getTeamTitle());
        teamService.updateTeam(teamDto);

        // ✅ Atualiza os membros da Equipe com arquivos
        if (dto.getTeamItems() != null && !dto.getTeamItems().isEmpty()) {
            for (TeamItemUpdateDto teamItem : dto.getTeamItems()) {
                teamService.updateMember(
                        teamItem.getId(),
                        teamItem.getFile(),
                        new TeamItemDto() // Pode adaptar se necessário
                );
            }
        }

        var updateContentDto = new UpdateContentDto(
                dto.getContentTitle(),
                dto.getContentSubTitle(),
                dto.getContentDescription(),
                dto.getContentMainImage()
        );
        contentService.updateContent(updateContentDto);

        if (dto.getContentCarousel() != null && !dto.getContentCarousel().isEmpty()) {
            for (ContentItemUpdateLandPageDto contentItem : dto.getContentCarousel()) {
                contentService.updateSlide(
                        contentItem.getId(),
                        contentItem.getFile(),
                        new ContentItemDto(contentItem.getImgText())
                );
            }
        }

        if (dto.getFaqItems() != null && !dto.getFaqItems().isEmpty()) {
            for (FaqItemUpdateLandPageDto faqItem : dto.getFaqItems()) {
                faqSectionService.updateItem(
                        faqItem.getId(),
                        new FaqItemUpdateDto(
                                faqItem.getQuestion(),
                                faqItem.getAnswer())
                );
            }
        }

        var contactUsUpdateDto = new ContactUsUpdateDto(dto.getContactTitle(), dto.getContactSubTitle(), dto.getContactDescription());
        contactUsService.updateSection(contactUsUpdateDto);

        // Atualiza o título e descrição do banner
        var bannerDto = new BannerUpdateDto(dto.getBannerTitle(), dto.getBannerDescription());
        bannerService.updateBanner(bannerDto);

        return ResponseEntity.ok().body("Atualizado com sucesso!");
    }



}
