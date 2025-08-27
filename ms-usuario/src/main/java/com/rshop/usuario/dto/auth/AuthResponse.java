package com.rshop.usuario.dto.auth;


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
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    /**
     * Token JWT para autenticação em requisições futuras
     */
    private String token;

    /**
     * Email do usuário autenticado
     */
    private String email;

    /**
     * Role do usuário para controle de acesso
     */
    private String role;
}