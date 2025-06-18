package com.ifce.jedi.service;


import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
import com.ifce.jedi.repository.ContactUsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
