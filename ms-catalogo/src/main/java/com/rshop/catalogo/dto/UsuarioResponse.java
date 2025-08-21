package com.rshop.catalogo.dto;



import java.util.List;
//dados do usuário logado
public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String role
) {}