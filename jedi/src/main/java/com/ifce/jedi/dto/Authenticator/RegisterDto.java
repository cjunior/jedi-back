package com.ifce.jedi.dto.Authenticator;

import com.ifce.jedi.model.User.UserRole;

public record RegisterDto(String email, String password, UserRole role) {
}
