package com.rshop.usuario.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de requisição para redefinição de senha do usuário.
 * Utilizado no processo de reset de senha após validação do token.
 */
@Data
@Schema(description = "DTO de requisição para redefinição de senha")
public class PasswordResetRequest {

    @NotBlank(message = "Token é obrigatório")
    @Schema(description = "Token de redefinição de senha recebido por email",
            example = "a1b2c3d4e5f6g7h8i9j0")
    private String token;

    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Schema(description = "Nova senha do usuário", example = "novaSenha123")
    private String novaSenha;
}
