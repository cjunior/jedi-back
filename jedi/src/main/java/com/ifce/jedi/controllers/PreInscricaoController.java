package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoComplementarDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDto;
import com.ifce.jedi.exception.custom.EmailSendingException;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.service.EmailService;
import com.ifce.jedi.service.PreInscricaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pre-inscricao")
public class PreInscricaoController {

    @Autowired
    private PreInscricaoService preInscricaoService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/inicial")
    public ResponseEntity<?> createRegistration(@Valid @RequestBody PreInscricaoDto preInscricaoDto) {
        PreInscricao preInscricao = preInscricaoService.createRegistration(preInscricaoDto);
        String token = preInscricao.getContinuationToken();
        String link = "https://jedi-front.vercel.app/pre-inscricao/continuar/" + token;

        try {
            emailService.sendLink(preInscricaoDto.email(), link);
        } catch (Exception e) {
            throw new EmailSendingException("Erro ao enviar e-mail de continuação.", e);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Pré-inscrição criada com sucesso.");
        response.put("token", token);
        response.put("expiration", preInscricao.getTokenExpiration());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/continuar/{token}")
    public ResponseEntity<?> continueRegistration(@PathVariable String token) {
        PreInscricao preInscricao = preInscricaoService.validateTokenOrThrow(token);

        PreInscricaoDto resumo = new PreInscricaoDto(
                preInscricao.getCompleteName(),
                preInscricao.getEmail(),
                preInscricao.getCellPhone()
        );

        return ResponseEntity.ok(resumo);
    }



    @PutMapping(value = "/continuar/{token}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> completeRegistration(
            @PathVariable String token,
            @Valid @ModelAttribute PreInscricaoComplementarDto preInscricaoDto) {
        preInscricaoService.completeRegistration(token, preInscricaoDto);
        return ResponseEntity.ok("Pré-inscrição finalizada com sucesso.");
    }

}
