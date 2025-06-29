package com.ifce.jedi.dto.Authenticator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticatorDto(
        @Email(message = "E-mail inválido.")
        @NotBlank(message = "E-mail é obrigatório.")
        String email,

        @NotBlank(message = "Senha é obrigatória.")
        String password) {
}
