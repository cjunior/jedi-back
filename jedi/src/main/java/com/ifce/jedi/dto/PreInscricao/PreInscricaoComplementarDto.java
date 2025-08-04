package com.ifce.jedi.dto.PreInscricao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record PreInscricaoComplementarDto(

        @NotNull(message = "A data de nascimento é obrigatória.")
        LocalDate birthDate,

        @NotBlank(message = "O município é obrigatório.")
        String municipality,

        @Pattern(
                regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
                message = "CPF inválido. Use o formato 000.000.000-00 ou apenas números."
        )
        String cpf,

        @NotBlank(message = "O RG é obrigatório.")
        String rg,


        @NotNull(message = "O documento é obrigatório.")
        MultipartFile document,

        @NotNull(message = "O comprovante de endereço é obrigatório.")
        MultipartFile proofOfAdress) {

}
