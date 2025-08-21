package com.rshop.catalogo.enums;

// src/main/java/com/rshop/common/enums/UserRole.java


public enum UserRole {
    ADMIN, USER, GERENTE, INATIVO, SYSTEM;

    public static UserRole fromString(String role) {
        if (role == null) return USER;
        try {
            return valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return USER;
        }
    }

    public boolean isAdmin() {
        return this == ADMIN || this == SYSTEM;
    }
}