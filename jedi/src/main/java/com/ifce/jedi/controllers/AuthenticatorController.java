package com.ifce.jedi.controllers;

import com.ifce.jedi.dto.Authenticator.*;
import com.ifce.jedi.service.AuthenticatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticatorController {

    @Autowired
    private AuthenticatorService authenticatorService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticatorDto authenticatorDto){
        String token = authenticatorService.login(authenticatorDto);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDto request) {
        authenticatorService.forgotPassword(request.email());
        return ResponseEntity.ok("Se este e-mail estiver cadastrado, um link de recuperação será enviado.");
    }

    @PutMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody @Valid ResetPasswordRequestDto request) {
        authenticatorService.resetPassword(token, request.newPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }

    @GetMapping("/reset-password/{token}")
    public ResponseEntity<TokenValidationResult> resetPassword(@PathVariable String token) {
        TokenValidationResult result = authenticatorService.checkToken(token);
        if (!result.isValid()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}

