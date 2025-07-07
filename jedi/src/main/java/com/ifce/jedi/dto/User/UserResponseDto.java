package com.ifce.jedi.dto.User;

import com.ifce.jedi.model.User.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private String photoUrl;
    private LocalDateTime createdAt;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getLogin();
        this.role = user.getRole().name();
        this.photoUrl = user.getPhotoUrl();
        this.createdAt = user.getCreatedAt();
    }


    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getPhotoUrl() { return photoUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}