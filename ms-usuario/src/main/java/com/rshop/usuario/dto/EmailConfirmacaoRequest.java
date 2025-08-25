package com.rshop.usuario.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class EmailConfirmacaoRequest {
    @NotBlank(message = "Token é obrigatório")
    private String token;
}
