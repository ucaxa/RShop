package com.ms.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private Long id;

    @NotNull(message = "produtoId obrigatório")
    private Long produtoId;

    private String produtoNome;

    @NotNull(message = "valorUnitario obrigatório")
    private BigDecimal valorUnitario;

    @NotNull(message = "quantidade obrigatória")
    @Min(value = 1, message = "quantidade mínima é 1")
    private Integer quantidade;
}
