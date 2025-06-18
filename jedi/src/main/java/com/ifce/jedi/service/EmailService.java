package com.ifce.jedi.service;

import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
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

    public void contactFormEmail(ContactFormEmailDto dto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("juliano.magalhaes06@aluno.ifce.edu.br"); // mudar depois para o email da fundação
        message.setFrom(dto.getName());
        message.setReplyTo(dto.getEmail());
        message.setSubject(dto.getSubject());
        message.setText(dto.getMessage());

        mailSender.send(message);
    }
}

