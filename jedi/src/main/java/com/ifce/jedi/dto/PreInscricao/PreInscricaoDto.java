package com.ifce.jedi.dto.PreInscricao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PreInscricaoDto(

        @NotBlank(message = "O nome completo é obrigatório.")
        String completeName,

        @Schema(example = "usuario@email.com")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Formato de e-mail inválido."
        )
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,


        @Pattern(
                regexp = "^(\\(\\d{2}\\)\\s?|\\d{2}\\s?)9\\d{4}-?\\d{4}$",
                message = "Celular inválido. Use formatos como (85) 99999-1234 ou 8599991234")
        String cellphone,

        @Schema(
                description = "Município selecionado. Caso não esteja na lista, use 'Outros' e informe em otherMunicipality.",
                example = "Belém"
        )
        @NotBlank(message = "O município é obrigatório.")
        String municipality,

        @Schema(
                description = "Município digitado caso tenha selecionado 'Outros'.",
                example = "Meu Município"
        )
        String otherMunicipality,

        @Schema(
                description = "Indica se o usuário aceitou os termos de uso.",
                example = "true",
                allowableValues = {"true", "false"}
        )
        @NotNull(message = "É obrigatório informar se os termos de uso foram aceitos.")
        Boolean acceptedTerms
) {
}
