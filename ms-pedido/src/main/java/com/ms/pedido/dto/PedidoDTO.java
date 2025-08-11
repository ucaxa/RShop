package com.ms.pedido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    private String numeroControle;

    private LocalDateTime dataCadastro;

    @NotBlank(message = "nomeCliente obrigatório")
    private String nomeCliente;

    private BigDecimal valorTotal;

    private Integer quantidadeTotal;

    private String codigoCliente;

    @NotEmpty(message = "itens é obrigatório")
    private List<ItemPedidoDTO> itens;
}
