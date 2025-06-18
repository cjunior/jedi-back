package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
import com.ifce.jedi.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contactus")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@ModelAttribute ContactFormEmailDto dto){
        contactUsService.sendEmail(dto);
        return ResponseEntity.ok("Email enviado com sucesso!");
    }
}
