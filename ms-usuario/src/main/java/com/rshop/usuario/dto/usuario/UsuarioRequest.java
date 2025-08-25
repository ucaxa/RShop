package com.rshop.usuario.dto.usuario;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UsuarioRequest {
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;

    private String role; // Opcional, pode ser definido como CLIENTE por padrão
}
