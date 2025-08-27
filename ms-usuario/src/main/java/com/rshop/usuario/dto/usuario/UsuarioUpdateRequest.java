package com.rshop.usuario.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de requisição para criação de usuário.
 * Inclui dados de autenticação e informações pessoais (antigamente no Perfil).
 */
@Data
@Schema(description = "DTO de requisição para atualização de usuário")

public class UsuarioUpdateRequest {
  //  @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Schema(description = "Email do usuário", example = "usuario@exemplo.com")
    private String email;

 //   @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;

    @Pattern(regexp = "CLIENTE|ADMIN|MANAGER", message = "Role deve ser CLIENTE, ADMIN ou MANAGER")
    @Schema(description = "Perfil do usuário", example = "CLIENTE", allowableValues = {"CLIENTE", "ADMIN", "MANAGER"})
    private String role;

    // Campos movidos do Perfil
  //  @NotBlank(message = "Nome completo é obrigatório")
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nomeCompleto;

    @Schema(description = "Telefone do usuário", example = "(11) 99999-9999")
    private String telefone;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Past
    private LocalDate dataNascimento;
}
