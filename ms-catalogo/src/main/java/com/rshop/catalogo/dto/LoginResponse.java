package com.rshop.catalogo.dto;


public record LoginResponse(
        String token,
        String tipo,
        Long expiresIn
) {
    public LoginResponse(String token) {
        this(token, "Bearer", 86400L); // 24 horas em segundos
    }
}

