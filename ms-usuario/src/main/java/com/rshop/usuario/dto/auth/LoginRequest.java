package com.rshop.usuario.dto.auth;


import lombok.*;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}

