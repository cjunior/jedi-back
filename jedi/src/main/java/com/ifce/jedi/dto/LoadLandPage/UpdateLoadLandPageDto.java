package com.ifce.jedi.dto.LoadLandPage;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

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
        private List<Long> bannerItemIds;

        @Schema(type = "string", format = "binary", description = "Imagens do banner")
        private MultipartFile[] bannerFiles;
        private List<String> bannerButtonText;
        private List<String> bannerButtonurl;
        private String teamTitle;
        private List<Long> teamItemIds;

        @Schema(type = "string", format = "binary", description = "Imagens do banner")
        private MultipartFile[] teamFiles;

        public UpdateLoadLandPageDto(MultipartFile headerFile, String headerText1, String headerText2, String headerText3, String headerText4, String bannerTitle, String headerButtonText, String bannerDescription, List<Long> bannerItemIds, MultipartFile[] bannerFiles, List<String> bannerButtonText, List<String> bannerButtonurl, String teamTitle, List<Long> teamItemIds, MultipartFile[] teamFiles) {
                this.headerFile = headerFile;
                this.headerText1 = headerText1;
                this.headerText2 = headerText2;
                this.headerText3 = headerText3;
                this.headerText4 = headerText4;
                this.bannerTitle = bannerTitle;
                this.headerButtonText = headerButtonText;
                this.bannerDescription = bannerDescription;
                this.bannerItemIds = bannerItemIds;
                this.bannerFiles = bannerFiles;
                this.bannerButtonText = bannerButtonText;
                this.bannerButtonurl = bannerButtonurl;
                this.teamTitle = teamTitle;
                this.teamItemIds = teamItemIds;
                this.teamFiles = teamFiles;
        }

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

        public List<Long> getBannerItemIds() {
                return bannerItemIds;
        }

        public void setBannerItemIds(List<Long> bannerItemIds) {
                this.bannerItemIds = bannerItemIds;
        }

        public MultipartFile[] getBannerFiles() {
                return bannerFiles;
        }

        public void setBannerFiles(MultipartFile[] bannerFiles) {
                this.bannerFiles = bannerFiles;
        }

        public List<String> getBannerButtonText() {
                return bannerButtonText;
        }

        public void setBannerButtonText(List<String> bannerButtonText) {
                this.bannerButtonText = bannerButtonText;
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

        public List<Long> getTeamItemIds() {
                return teamItemIds;
        }

        public void setTeamItemIds(List<Long> teamItemIds) {
                this.teamItemIds = teamItemIds;
        }

        public MultipartFile[] getTeamFiles() {
                return teamFiles;
        }

        public void setTeamFiles(MultipartFile[] teamFiles) {
                this.teamFiles = teamFiles;
        }
}
