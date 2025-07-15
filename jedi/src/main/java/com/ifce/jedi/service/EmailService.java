package com.ifce.jedi.service;

import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendLink(String destinatario, String link) {
        MimeMessage mensagem = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject("Continue sua pré-inscrição");

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f6f8fa;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        background-color: #ffffff;
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 40px 30px;
                        border-radius: 8px;
                        box-shadow: 0 0 10px rgba(0,0,0,0.08);
                        text-align: center;
                    }
                    h2 {
                        color: #333333;
                        font-size: 24px;
                        margin-top: 20px;
                        margin-bottom: 20px;
                    }
                    p {
                        font-size: 16px;
                        color: #444444;
                        margin-bottom: 20px;
                    }
                    .button {
                        display: inline-block;
                        background-color: #1a2b1c;
                        color: #ffffff !important;
                        padding: 14px 24px;
                        text-decoration: none;
                        border-radius: 6px;
                        font-weight: bold;
                        font-size: 16px;
                        margin: 20px 0;
                    }
                    .footer {
                        margin-top: 40px;
                        font-size: 12px;
                        color: #999999;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <img src="cid:logoCid" width="180" alt="Logo Jovens Empreendedores Digitais" style="margin-bottom: 10px;" />
                    <h2>Olá!</h2>
                    <p>Recebemos sua intenção de pré-inscrição.<br>Para continuar, clique no botão abaixo:</p>
                    <a href="%s" class="button">Continuar Pré-inscrição</a>
                    <p>Se você não iniciou esta ação, pode ignorar este e-mail.</p>
                    <div class="footer">
                        © 2025 IFCE - Todos os direitos reservados.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(link);



            helper.setText(htmlContent, true);
            ClassPathResource image = new ClassPathResource("static/images/logo.png");
            helper.addInline("logoCid", image);
            mailSender.send(mensagem);
        } catch (MessagingException e) {
            // log e tratamento
            e.printStackTrace();
        }
    }

    public void contactFormEmail(ContactFormEmailDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("juliano.magalhaes06@aluno.ifce.edu.br");
        message.setFrom("aglis.silva62@aluno.ifce.edu.br");
        message.setReplyTo(dto.getEmail());
        message.setSubject(dto.getSubject());
        message.setText("Nome: " + dto.getName() + "\n\nMensagem:\n" + dto.getMessage());

        mailSender.send(message);
    }
}

