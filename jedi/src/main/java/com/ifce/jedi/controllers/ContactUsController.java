package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Banner.BannerResponseDto;
import com.ifce.jedi.dto.Banner.BannerUpdateDto;
import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
import com.ifce.jedi.dto.ContactUs.ContactUsResponseDto;
import com.ifce.jedi.dto.ContactUs.ContactUsUpdateDto;
import com.ifce.jedi.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@ModelAttribute ContactFormEmailDto dto){
        contactUsService.sendEmail(dto);
        return ResponseEntity.ok("Email enviado com sucesso!");
    }

    @GetMapping("/get")
    public ResponseEntity<ContactUsResponseDto> getSection(){
        ContactUsResponseDto section = contactUsService.getSection();
        return section == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(section);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactUsResponseDto> update(@RequestBody ContactUsUpdateDto dto) {
        return ResponseEntity.ok(contactUsService.updateSection(dto));
    }
}
