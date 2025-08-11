package com.rshop.catalogo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    private LocalDateTime dataCadastro;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;

    private String categoriaDescricao;
}