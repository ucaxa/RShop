package com.rshop.usuario.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PerfilUpdateRequest {
    private String nomeCompleto;
    private String telefone;
    private LocalDate dataNascimento;
}
