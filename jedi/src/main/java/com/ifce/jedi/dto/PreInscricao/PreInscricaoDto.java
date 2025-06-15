package com.ifce.jedi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PreInscricaoDto(

        @NotBlank(message = "O nome completo é obrigatório.")
        String completeName,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "Número de telefone obrigatório.")
        String cellphone) {
}
