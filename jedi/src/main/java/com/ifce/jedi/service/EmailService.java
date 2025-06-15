package com.ifce.jedi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendLink(String destinatario, String link) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Continue sua pré-inscrição");
        mensagem.setText("Olá! Para continuar sua pré-inscrição, acesse o link: " + link);

        mailSender.send(mensagem);
    }
}

