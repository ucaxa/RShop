package com.rshop.usuario.dto;


import lombok.*;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}

