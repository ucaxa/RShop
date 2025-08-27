package com.rshop.usuario.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de requisição para registro de novo usuário.
 * Utilizado no processo de cadastro inicial.
 */
@Data
@Schema(description = "DTO de requisição para registro de novo usuário")
public class RegisterRequest {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Schema(description = "Email do usuário", example = "usuario@exemplo.com")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Schema(description = "Nome completo do usuário", example = "João Silva Santos")
    private String nomeCompleto;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    @Schema(description = "Telefone para contato", example = "(11) 99999-9999")
    private String telefone;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}", message = "CPF deve ser válido (com ou sem pontuação)")
    @Schema(description = "CPF do usuário (com ou sem pontuação)", example = "12345678900")
    private String cpf;

    @Past
    @NotBlank
    private LocalDate dataNascimento;
}
