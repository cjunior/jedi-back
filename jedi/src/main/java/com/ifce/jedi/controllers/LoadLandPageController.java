package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.*;
import com.ifce.jedi.dto.Blog.BlogItemUpdateDto;
import com.ifce.jedi.dto.ContactUs.ContactUsUpdateDto;
import com.ifce.jedi.dto.Contents.ContentItemDto;
import com.ifce.jedi.dto.Contents.UpdateContentDto;
import com.ifce.jedi.dto.FaqSection.FaqItemUpdateDto;
import com.ifce.jedi.dto.FaqSection.FaqSectionHeaderUpdateDto;
import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.LoadLandPage.*;
import com.ifce.jedi.dto.PresentationSection.PresentationSectionUpdateDto;
import com.ifce.jedi.dto.Rede.imagemRedeJedUpdateWrapperDto;
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
import java.util.*;

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

    @Autowired
    private RedeJediSectionService redeJediSectionService;

    @Autowired
    private RedeJediImageService redeJediImageService;

    @Autowired
    private BlogSectionService blogSectionService;

    @GetMapping("/get")
    public ResponseEntity<LoadLandPageDto> getAll() {
        LoadLandPageDto landPage = loadLandPageService.getAll();
        return landPage == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(landPage);
    }

    @PutMapping(value = "/update-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAll(@ModelAttribute UpdateLoadLandPageDto dto) throws IOException {

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

        var teamDto = new TeamUpdateDto(dto.getTeamTitle());
        teamService.updateTeam(teamDto);

        if (dto.getTeamItems() != null && !dto.getTeamItems().isEmpty()) {
            for (TeamItemUpdateDto teamItem : dto.getTeamItems()) {
                teamService.updateMember(
                        teamItem.getId(),
                        teamItem.getFile(),
                        new TeamItemDto()
                );
            }
        }

        var updateContentDto = new UpdateContentDto(
                dto.getContentTitle(),
                dto.getContentSubTitle(),
                dto.getContentDescription(),
                dto.getContentMainImage(),
                dto.getContentMainImageText()
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
        faqSectionService.updateHeader(new FaqSectionHeaderUpdateDto(dto.getFaqTitle(), dto.getFaqSubtitle()));
        var contactUsUpdateDto = new ContactUsUpdateDto(dto.getContactTitle(), dto.getContactSubTitle(), dto.getContactDescription());
        contactUsService.updateSection(contactUsUpdateDto);

        redeJediSectionService.atualizarTitulo(1L, dto.getRedeTitle());



        List<Long> ids = new ArrayList<>();
        List<MultipartFile> imagens = new ArrayList<>();
        if(dto.getRedeFiles() != null){
            for (imagemRedeJedUpdateWrapperDto obj : dto.getRedeFiles()){
                ids.add(obj.getId());
                if(obj.getFile()!= null){
                    imagens.add(obj.getFile());
                }
            }
            redeJediImageService.updateMultipleImages(ids, imagens);
        }


        if(dto.getBlogItems() != null){
            for (blogitemsWrapperDto obj : dto.getBlogItems()){
                blogSectionService.updateBlogItem(obj.getId(), new BlogItemUpdateDto(
                        obj.getTitle(),
                        obj.getAuthor(),
                        obj.getDate(),
                        obj.getReadingTime(),
                        obj.getImageDescription(),
                        obj.getDescription(), // Novo campo
                        obj.getFile(),
                        obj.getIconFile() // Novo campo
                ));
            }
        }



        var bannerDto = new BannerUpdateDto(dto.getBannerTitle(), dto.getBannerDescription());
        bannerService.updateBanner(bannerDto);

        return ResponseEntity.ok().body("Atualizado com sucesso!");
    }



}
