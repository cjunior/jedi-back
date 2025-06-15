package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoComplementarDto;
import com.ifce.jedi.dto.PreInscricao.PreInscricaoDto;
import com.ifce.jedi.model.User.PreInscricao;
import com.ifce.jedi.service.EmailService;
import com.ifce.jedi.service.PreInscricaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        String link = "http://localhost:8080/pre-inscricao/continuar/" + token;

        emailService.sendLink(preInscricaoDto.email(), link);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Pré-inscrição criada com sucesso.");
        response.put("token", token);
        response.put("expiration", preInscricao.getTokenExpiration());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/continuar/{token}")
    public ResponseEntity<?> continueRegistration(@PathVariable String token) {
        Optional<PreInscricao> preOpt = preInscricaoService.findByContinuationToken(token);

        if (preOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Token inválido.");
        }

        PreInscricao preInscricao = preOpt.get();

        if (preInscricao.getTokenExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(410).body("Token expirado.");
        }

        PreInscricaoDto resumo = new PreInscricaoDto(
                preInscricao.getCompleteName(),
                preInscricao.getEmail(),
                preInscricao.getCellPhone());

        return ResponseEntity.ok(resumo);
    }

    @PutMapping("/continuar/{token}")
    public ResponseEntity<?> completeRegistration(
            @PathVariable String token,
            @Valid @ModelAttribute PreInscricaoComplementarDto preInscricaoDto) {
        preInscricaoService.completeRegistration(token, preInscricaoDto);
        return ResponseEntity.ok("Pre-inscrição finalizada com sucesso.");
    }
}
