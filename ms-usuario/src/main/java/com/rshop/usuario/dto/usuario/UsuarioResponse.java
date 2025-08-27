package com.rshop.usuario.dto.usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO de resposta para operações de usuário.
 * Contém informações da conta e dados pessoais do usuário.
 */
@Data
@Schema(description = "DTO de resposta para informações de usuário")
public class UsuarioResponse {

    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Schema(description = "Email do usuário", example = "usuario@exemplo.com")
    private String email;

    @Schema(description = "Perfil de acesso do usuário", example = "CLIENTE", allowableValues = {"CLIENTE", "ADMIN", "MANAGER"})
    private String role;

    @Schema(description = "Indica se o usuário está ativo", example = "true")
    private boolean ativo;

    @Schema(description = "Data e hora de criação da conta", example = "2023-01-15T10:30:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora do último login", example = "2023-06-20T14:25:00")
    private LocalDateTime dataUltimoLogin;

    // Campos movidos do PerfilResponse
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nomeCompleto;

    @Schema(description = "Telefone do usuário", example = "(11) 99999-9999")
    private String telefone;

    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Define se usuário está habolitado")
    private boolean enabled = false;
}