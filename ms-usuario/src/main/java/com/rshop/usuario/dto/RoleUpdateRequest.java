package com.rshop.usuario.dto;

import com.rshop.usuario.model.Role;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class RoleUpdateRequest {
    @NotNull(message = "Role é obrigatória")
    private Role role;
}