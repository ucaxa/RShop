package com.rshop.usuario.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de autenticação
 *
 * <p>Representa a resposta retornada após autenticação bem-sucedida,
 * contendo o token JWT e informações básicas do usuário autenticado.</p>
 *
 * <p><strong>Campos:</strong></p>
 * <ul>
 *   <li><code>token</code>: Token JWT para autenticação em requisições futuras</li>
 *   <li><code>email</code>: Email do usuário autenticado</li>
 *   <li><code>role</code>: Role do usuário para controle de acesso no frontend</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta para autenticação bem-sucedida")
public class AuthResponse {

    /**
     * Token JWT para autenticação em requisições futuras
     */
    @Schema(description = "Token JWT para autenticação em requisições futuras",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String token;

    /**
     * Email do usuário autenticado
     */
    @Schema(description = "Email do usuário autenticado", example = "usuario@exemplo.com")
    private String email;

    /**
     * Role do usuário para controle de acesso
     */
    @Schema(description = "Perfil de acesso do usuário",
            example = "CLIENTE",
            allowableValues = {"CLIENTE", "ADMIN"})
    private String role;
}