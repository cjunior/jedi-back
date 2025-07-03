package com.ifce.jedi.dto.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ifce.jedi.model.User.User;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL) // Opcional: ignora campos nulos
public class UserTableResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private boolean hasPhoto;

    // Construtor
    public UserTableResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getLogin();
        this.role = user.getRole().name();
        this.hasPhoto = user.getPhotoUrl() != null;
    }

    // Getters (OBRIGATÓRIOS para o Jackson)
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isHasPhoto() { return hasPhoto; } // ou getHasPhoto()
}