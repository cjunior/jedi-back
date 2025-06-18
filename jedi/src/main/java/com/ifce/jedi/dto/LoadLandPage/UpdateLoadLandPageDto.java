package com.ifce.jedi.dto.LoadLandPage;


import com.ifce.jedi.dto.Banner.BannerItemUpdate;
import com.ifce.jedi.dto.Team.TeamItemUpdateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class UpdateLoadLandPageDto {
        private MultipartFile headerFile;
        private String headerText1;
        private String headerText2;
        private String headerText3;
        private String headerText4;
        private String headerButtonText;
        private String bannerTitle;
        private String bannerDescription;

        private List<BannerItemUpdate> bannerItems = new ArrayList<>();
        private List<String> bannerButtonurl;
        private String teamTitle;
        private List<TeamItemUpdateDto> teamItems = new ArrayList<>();

        public MultipartFile getHeaderFile() {
                return headerFile;
        }

        public void setHeaderFile(MultipartFile headerFile) {
                this.headerFile = headerFile;
        }

        public String getHeaderText1() {
                return headerText1;
        }

        public void setHeaderText1(String headerText1) {
                this.headerText1 = headerText1;
        }

        public String getHeaderText2() {
                return headerText2;
        }

        public void setHeaderText2(String headerText2) {
                this.headerText2 = headerText2;
        }

        public String getHeaderText3() {
                return headerText3;
        }

        public void setHeaderText3(String headerText3) {
                this.headerText3 = headerText3;
        }

        public String getHeaderText4() {
                return headerText4;
        }

        public void setHeaderText4(String headerText4) {
                this.headerText4 = headerText4;
        }

        public String getHeaderButtonText() {
                return headerButtonText;
        }

        public void setHeaderButtonText(String headerButtonText) {
                this.headerButtonText = headerButtonText;
        }

        public String getBannerTitle() {
                return bannerTitle;
        }

        public void setBannerTitle(String bannerTitle) {
                this.bannerTitle = bannerTitle;
        }

        public String getBannerDescription() {
                return bannerDescription;
        }

        public void setBannerDescription(String bannerDescription) {
                this.bannerDescription = bannerDescription;
        }

        public List<BannerItemUpdate> getBannerItems() {
                return bannerItems;
        }

        public void setBannerItems(List<BannerItemUpdate> bannerItems) {
                this.bannerItems = bannerItems;
        }

        public List<String> getBannerButtonurl() {
                return bannerButtonurl;
        }

        public void setBannerButtonurl(List<String> bannerButtonurl) {
                this.bannerButtonurl = bannerButtonurl;
        }

        public String getTeamTitle() {
                return teamTitle;
        }

        public void setTeamTitle(String teamTitle) {
                this.teamTitle = teamTitle;
        }

        public List<TeamItemUpdateDto> getTeamItems() {
                return teamItems;
        }

        public void setTeamItems(List<TeamItemUpdateDto> teamItems) {
                this.teamItems = teamItems;
        }

        public UpdateLoadLandPageDto() {
        }

        public UpdateLoadLandPageDto(MultipartFile headerFile, String headerText1, String headerText2, String headerText3, String headerText4, String headerButtonText, String bannerTitle, String bannerDescription, List<BannerItemUpdate> bannerItems, List<String> bannerButtonurl, String teamTitle, List<TeamItemUpdateDto> teamItems) {
                this.headerFile = headerFile;
                this.headerText1 = headerText1;
                this.headerText2 = headerText2;
                this.headerText3 = headerText3;
                this.headerText4 = headerText4;
                this.headerButtonText = headerButtonText;
                this.bannerTitle = bannerTitle;
                this.bannerDescription = bannerDescription;
                this.bannerItems = bannerItems;
                this.bannerButtonurl = bannerButtonurl;
                this.teamTitle = teamTitle;
                this.teamItems = teamItems;
        }
}
