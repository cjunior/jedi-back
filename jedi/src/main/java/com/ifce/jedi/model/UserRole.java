package com.ifce.jedi.model;

public enum UserRole {
    ADMIN("admin"),
    GERENTE("gerente"),
    VISITANTE("visitante");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
