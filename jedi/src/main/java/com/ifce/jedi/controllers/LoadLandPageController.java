package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemDto;
import com.ifce.jedi.dto.Banner.BannerSlideUpdateDto;
import com.ifce.jedi.dto.Banner.BannerUpdateDto;
import com.ifce.jedi.dto.Header.HeaderDto;
import com.ifce.jedi.dto.LoadLandPage.LoadLandPageDto;
import com.ifce.jedi.dto.LoadLandPage.UpdateLoadLandPageDto;
import com.ifce.jedi.dto.Team.TeamItemDto;
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
import java.util.List;

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

    @GetMapping("/get")
    public ResponseEntity<LoadLandPageDto> getHeader() {
        LoadLandPageDto landPage = loadLandPageService.getAll();
        return landPage == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(landPage);
    }

    @PutMapping(value = "/update-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAll(@ModelAttribute UpdateLoadLandPageDto dto) throws IOException {
        MultipartFile headerFile = dto.getHeaderFile();
        String headerText1 = dto.getHeaderText1();
        String headerText2 = dto.getHeaderText2();
        String headerText3 = dto.getHeaderText3();
        String headerText4 = dto.getHeaderText4();
        String headerButtonText = dto.getHeaderButtonText();
        String bannerTitle = dto.getBannerTitle();
        String bannerDescription = dto.getBannerDescription();
        List<Long> bannerItemIds = dto.getBannerItemIds();
        MultipartFile[] bannerFiles = dto.getBannerFiles();
        List<String> bannerButtonText = dto.getBannerButtonText();
        List<String> bannerButtonurl = dto.getBannerButtonurl();
        String teamTitle = dto.getTeamTitle();
        List<Long> teamItemIds = dto.getTeamItemIds();
        MultipartFile[] teamFiles = dto.getTeamFiles();

        if (bannerItemIds.size() != bannerButtonText.size() || bannerFiles.length != bannerButtonText.size()) {
            return ResponseEntity.badRequest().body("Números de atributos não correspondem.");
        }
        var headerDto = new HeaderDto(headerFile, headerText1, headerText2, headerText3, headerText4, headerButtonText);
        headerService.updateHeader(headerDto);

        var bannerDto = new BannerUpdateDto(bannerTitle, bannerDescription);
        bannerService.updateBanner(bannerDto);

        if(!bannerItemIds.isEmpty()){
            if(bannerFiles.length > 0){
                for (int i = 0; i < bannerItemIds.size(); i++) {
                    String url = (bannerButtonurl != null && i < bannerButtonurl.size()) ? bannerButtonurl.get(i) : "";
                    BannerItemDto itemDto = new BannerItemDto(bannerButtonText.get(i), url);
                    bannerService.updateSlide(bannerItemIds.get(i), bannerFiles[i], itemDto);
                }
            }
            else {
                for (int i = 0; i < bannerItemIds.size(); i++) {
                    String url = (bannerButtonurl != null && i < bannerButtonurl.size()) ? bannerButtonurl.get(i) : "";
                    BannerItemDto itemDto = new BannerItemDto(bannerButtonText.get(i), url);
                    bannerService.updateSlide(bannerItemIds.get(i), null, itemDto);
                }
            }
        }

        var teamDto = new TeamUpdateDto(teamTitle);
        teamService.updateTeam(teamDto);

        if (teamItemIds.size() != teamFiles.length) {
            return ResponseEntity.badRequest().body("Número de IDs e arquivos não correspondem.");
        }
        if(!teamItemIds.isEmpty()){
            TeamItemDto teamItemDto = new TeamItemDto();
            for (int i = 0; i < teamItemIds.size(); i++){
                teamService.updateMember(teamItemIds.get(i), teamFiles[i], teamItemDto);
            }
        }

        return ResponseEntity.ok().body("Atualizado com sucesso!");
    }

}
