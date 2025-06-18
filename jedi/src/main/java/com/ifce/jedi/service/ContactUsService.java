package com.ifce.jedi.service;


import com.ifce.jedi.dto.Banner.BannerDto;
import com.ifce.jedi.dto.Banner.BannerItemResponseDto;
import com.ifce.jedi.dto.Banner.BannerResponseDto;
import com.ifce.jedi.dto.Banner.BannerUpdateDto;
import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
import com.ifce.jedi.dto.ContactUs.ContactUsDto;
import com.ifce.jedi.dto.ContactUs.ContactUsResponseDto;
import com.ifce.jedi.dto.ContactUs.ContactUsUpdateDto;
import com.ifce.jedi.model.SecoesSite.Banner.Banner;
import com.ifce.jedi.model.SecoesSite.Banner.BannerItem;
import com.ifce.jedi.model.SecoesSite.ContactUs.ContactUs;
import com.ifce.jedi.repository.ContactUsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactUsService {
    @Autowired
    private ContactUsRepository contactUsRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public ResponseEntity<?>  sendEmail(ContactFormEmailDto dto){
        emailService.contactFormEmail(dto);
        return ResponseEntity.ok("Email enviado com sucesso!");
    }

    @Transactional
    public ContactUsResponseDto createSection(ContactUsDto dto) {
        ContactUs contactUs = new ContactUs();
        contactUs.setTitle(dto.getTitle());
        contactUs.setSubTitle(dto.getSubTitle());
        contactUs.setDescription(dto.getDescription());

        ContactUs saved = contactUsRepository.save(contactUs);

        return toResponse(saved);
    }

    @Transactional
    public ContactUsResponseDto getSection() {
        return contactUsRepository.findAll().stream()
                .findFirst()
                .map(this::toResponse)
                .orElse(null);
    }

    @Transactional
    public ContactUsResponseDto updateSection(ContactUsUpdateDto dto) {
        ContactUs contactUs = contactUsRepository.findAll().stream().findFirst().orElseThrow(
                () -> new RuntimeException("Section não encontrada.")
        );

        contactUs.setTitle(dto.getTitle());
        contactUs.setSubTitle(dto.getSubTitle());
        contactUs.setDescription(dto.getDescription());

        ContactUs updated = contactUsRepository.save(contactUs);
        return toResponse(updated);
    }


    private ContactUsResponseDto toResponse(ContactUs contactUs) {
        return new ContactUsResponseDto(
                contactUs.getId(),
                contactUs.getTitle(),
                contactUs.getSubTitle(),
                contactUs.getDescription()
        );
    }
}
