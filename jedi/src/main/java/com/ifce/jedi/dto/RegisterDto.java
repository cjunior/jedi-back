package com.ifce.jedi.dto;

import com.ifce.jedi.model.UserRole;

public record RegisterDto(String email, String password, UserRole role) {
}
