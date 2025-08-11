package com.rshop.usuario.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String senha;
}

