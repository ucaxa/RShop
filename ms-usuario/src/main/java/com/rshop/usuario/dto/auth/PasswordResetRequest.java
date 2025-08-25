package com.rshop.usuario.dto.auth;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class PasswordResetRequest {
    @NotBlank(message = "Token é obrigatório")
    private String token;

    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String novaSenha;
}
