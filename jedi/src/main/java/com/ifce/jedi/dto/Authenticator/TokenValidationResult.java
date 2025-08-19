package com.ifce.jedi.dto.Authenticator;

import java.time.LocalDateTime;

public class TokenValidationResult {
    private final boolean valid;
    private final String message;
    private final LocalDateTime expiryDate;

    public TokenValidationResult(boolean valid, String message, LocalDateTime expiryDate) {
        this.valid = valid;
        this.message = message;
        this.expiryDate = expiryDate;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
