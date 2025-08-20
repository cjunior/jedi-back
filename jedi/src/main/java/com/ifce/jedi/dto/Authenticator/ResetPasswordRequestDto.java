package com.ifce.jedi.dto.Authenticator;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequestDto(
        @NotBlank String newPassword
) {
}
