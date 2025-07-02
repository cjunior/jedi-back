package com.ifce.jedi.model.User;

public enum UserRole {
    ADMIN("admin"),
    GERENTE("gerente"),
    BLOG("blog");
    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
