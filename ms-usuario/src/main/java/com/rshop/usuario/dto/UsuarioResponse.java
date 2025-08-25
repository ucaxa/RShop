package com.rshop.usuario.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsuarioResponse {
    private Long id;
    private String email;
    private String role;
    private boolean enabled;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimoLogin;
    private PerfilResponse perfil;
}