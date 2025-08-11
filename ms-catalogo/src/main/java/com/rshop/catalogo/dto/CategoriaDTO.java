package com.rshop.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import  lombok.NoArgsConstructor;
import  lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
}

