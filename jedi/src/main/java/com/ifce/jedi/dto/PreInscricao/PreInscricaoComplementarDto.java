package com.ifce.jedi.dto.PreInscricao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record PreInscricaoComplementarDto(

        @NotNull(message = "A data de nascimento é obrigatória.")
        LocalDate birthDate,

        @NotBlank(message = "O município é obrigatório.")
        String municipality,

        @NotBlank(message = "O CPF é obrigatório.")
        String cpf,

        @NotBlank(message = "O RG é obrigatório.")
        String rg,

        @NotNull(message = "O documento é obrigatório.")
        MultipartFile document,

        @NotNull(message = "O comprovante de endereço é obrigatório.")
        MultipartFile proofOfAdress) {

}
