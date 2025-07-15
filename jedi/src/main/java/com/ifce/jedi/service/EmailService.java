package com.ifce.jedi.service;

import com.ifce.jedi.dto.ContactUs.ContactFormEmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
                        body { font-family: Arial, sans-serif; background-color: #f6f8fa; padding: 20px; color: #333; }
                        .container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }
                        .button { background-color: #0056b3; color: white; padding: 15px 25px; border-radius: 5px; text-decoration: none; font-weight: bold; }
                        .footer { margin-top: 30px; font-size: 0.9em; color: #777; text-align: center; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Olá!</h2>
                        <p>Recebemos sua intenção de pré-inscrição. Para continuar, clique no botão abaixo:</p>
                        <p style="text-align: center;">
                            <a href="%s" class="button">Continuar Pré-inscrição</a>
                        </p>
                        <p>Se você não iniciou esta ação, pode ignorar este e-mail.</p>
                        <div class="footer">© 2025 IFCE - Todos os direitos reservados.</div>
                    </div>
                </body>
                </html>
                """.formatted(link);

            helper.setText(htmlContent, true);
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

