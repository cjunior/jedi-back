package com.ifce.jedi.dto.LoadLandPage;


import com.ifce.jedi.dto.Banner.BannerItemUpdate;
import com.ifce.jedi.dto.Rede.imagemRedeJedUpdateWrapperDto;
import com.ifce.jedi.dto.Rede.imagemRedeJedWrapperDto;
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

        private String presentationSectionTitle;
        private String presentationSectionFirstDescription;
        private String presentationSectionSecondDescription;
        private String presentationSectionFirstStatistic;
        private String presentationSectionSecondStatistic;
        private String presentationSectionImgDescription;

        private MultipartFile presentationSectionFile;

        private String teamTitle;
        private List<TeamItemUpdateDto> teamItems = new ArrayList<>();

        private String contentTitle;
        private String contentSubTitle;
        private String contentDescription;
        private MultipartFile contentMainImage;
        private String contentMainImageText;

        private List<ContentItemUpdateLandPageDto> contentCarousel = new ArrayList<>();

        private String faqTitle;
        private String faqSubtitle;
        private List<FaqItemUpdateLandPageDto> faqItems = new ArrayList<>();

        private String contactTitle;
        private String contactSubTitle;
        private String contactDescription;

        private String redeTitle;

        private List<imagemRedeJedUpdateWrapperDto> redeFiles;

        private List<blogitemsWrapperDto> blogItems;

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

        public String getPresentationSectionTitle() {
                return presentationSectionTitle;
        }

        public void setPresentationSectionTitle(String presentationSectionTitle) {
                this.presentationSectionTitle = presentationSectionTitle;
        }

        public String getPresentationSectionFirstDescription() {
                return presentationSectionFirstDescription;
        }

        public void setPresentationSectionFirstDescription(String presentationSectionFirstDescription) {
                this.presentationSectionFirstDescription = presentationSectionFirstDescription;
        }

        public String getPresentationSectionSecondDescription() {
                return presentationSectionSecondDescription;
        }

        public void setPresentationSectionSecondDescription(String presentationSectionSecondDescription) {
                this.presentationSectionSecondDescription = presentationSectionSecondDescription;
        }

        public String getPresentationSectionFirstStatistic() {
                return presentationSectionFirstStatistic;
        }

        public void setPresentationSectionFirstStatistic(String presentationSectionFirstStatistic) {
                this.presentationSectionFirstStatistic = presentationSectionFirstStatistic;
        }

        public String getPresentationSectionSecondStatistic() {
                return presentationSectionSecondStatistic;
        }

        public void setPresentationSectionSecondStatistic(String presentationSectionSecondStatistic) {
                this.presentationSectionSecondStatistic = presentationSectionSecondStatistic;
        }

        public String getPresentationSectionImgDescription() {
                return presentationSectionImgDescription;
        }

        public void setPresentationSectionImgDescription(String presentationSectionImgDescription) {
                this.presentationSectionImgDescription = presentationSectionImgDescription;
        }

        public MultipartFile getPresentationSectionFile() {
                return presentationSectionFile;
        }

        public void setPresentationSectionFile(MultipartFile presentationSectionFile) {
                this.presentationSectionFile = presentationSectionFile;
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

        public String getContentTitle() {
                return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
                this.contentTitle = contentTitle;
        }

        public String getContentSubTitle() {
                return contentSubTitle;
        }

        public void setContentSubTitle(String contentSubTitle) {
                this.contentSubTitle = contentSubTitle;
        }

        public String getContentDescription() {
                return contentDescription;
        }

        public void setContentDescription(String contentDescription) {
                this.contentDescription = contentDescription;
        }

        public MultipartFile getContentMainImage() {
                return contentMainImage;
        }

        public void setContentMainImage(MultipartFile contentMainImage) {
                this.contentMainImage = contentMainImage;
        }

        public String getContentMainImageText() {
                return contentMainImageText;
        }

        public void setContentMainImageText(String contentMainImageText) {
                this.contentMainImageText = contentMainImageText;
        }

        public List<ContentItemUpdateLandPageDto> getContentCarousel() {
                return contentCarousel;
        }

        public void setContentCarousel(List<ContentItemUpdateLandPageDto> contentCarousel) {
                this.contentCarousel = contentCarousel;
        }

        public String getFaqTitle() {
                return faqTitle;
        }

        public void setFaqTitle(String faqTitle) {
                this.faqTitle = faqTitle;
        }

        public String getFaqSubtitle() {
                return faqSubtitle;
        }

        public void setFaqSubtitle(String faqSubtitle) {
                this.faqSubtitle = faqSubtitle;
        }

        public List<FaqItemUpdateLandPageDto> getFaqItems() {
                return faqItems;
        }

        public void setFaqItems(List<FaqItemUpdateLandPageDto> faqItems) {
                this.faqItems = faqItems;
        }

        public String getContactTitle() {
                return contactTitle;
        }

        public void setContactTitle(String contactTitle) {
                this.contactTitle = contactTitle;
        }

        public String getContactSubTitle() {
                return contactSubTitle;
        }

        public void setContactSubTitle(String contactSubTitle) {
                this.contactSubTitle = contactSubTitle;
        }

        public String getContactDescription() {
                return contactDescription;
        }

        public void setContactDescription(String contactDescription) {
                this.contactDescription = contactDescription;
        }

        public String getRedeTitle() {
                return redeTitle;
        }

        public void setRedeTitle(String redeTitle) {
                this.redeTitle = redeTitle;
        }

        public List<imagemRedeJedUpdateWrapperDto> getRedeFiles() {
                return redeFiles;
        }

        public void setRedeFiles(List<imagemRedeJedUpdateWrapperDto> redeFiles) {
                this.redeFiles = redeFiles;
        }

        public List<blogitemsWrapperDto> getBlogItems() {
                return blogItems;
        }

        public void setBlogItems(List<blogitemsWrapperDto> blogItems) {
                this.blogItems = blogItems;
        }

        public UpdateLoadLandPageDto() {
        }

        public UpdateLoadLandPageDto(MultipartFile headerFile, String headerText1, String headerText2, String headerText3, String headerText4, String headerButtonText, String bannerTitle, String bannerDescription, List<BannerItemUpdate> bannerItems, List<String> bannerButtonurl, String presentationSectionTitle, String presentationSectionFirstDescription, String presentationSectionSecondDescription, String presentationSectionFirstStatistic, String presentationSectionSecondStatistic, String presentationSectionImgDescription, MultipartFile presentationSectionFile, String teamTitle, List<TeamItemUpdateDto> teamItems, String contentTitle, String contentSubTitle, String contentDescription, MultipartFile contentMainImage, String contentMainImageText, List<ContentItemUpdateLandPageDto> contentCarousel, String faqTitle, String faqSubtitle, List<FaqItemUpdateLandPageDto> faqItems, String contactTitle, String contactSubTitle, String contactDescription, String redeTitle, List<imagemRedeJedUpdateWrapperDto> redeFiles, List<blogitemsWrapperDto> blogItems) {
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
                this.presentationSectionTitle = presentationSectionTitle;
                this.presentationSectionFirstDescription = presentationSectionFirstDescription;
                this.presentationSectionSecondDescription = presentationSectionSecondDescription;
                this.presentationSectionFirstStatistic = presentationSectionFirstStatistic;
                this.presentationSectionSecondStatistic = presentationSectionSecondStatistic;
                this.presentationSectionImgDescription = presentationSectionImgDescription;
                this.presentationSectionFile = presentationSectionFile;
                this.teamTitle = teamTitle;
                this.teamItems = teamItems;
                this.contentTitle = contentTitle;
                this.contentSubTitle = contentSubTitle;
                this.contentDescription = contentDescription;
                this.contentMainImage = contentMainImage;
                this.contentMainImageText = contentMainImageText;
                this.contentCarousel = contentCarousel;
                this.faqTitle = faqTitle;
                this.faqSubtitle = faqSubtitle;
                this.faqItems = faqItems;
                this.contactTitle = contactTitle;
                this.contactSubTitle = contactSubTitle;
                this.contactDescription = contactDescription;
                this.redeTitle = redeTitle;
                this.redeFiles = redeFiles;
                this.blogItems = blogItems;
        }
}
