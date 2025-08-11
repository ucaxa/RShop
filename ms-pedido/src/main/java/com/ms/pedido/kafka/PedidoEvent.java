/*package com.ms.pedido.kafka;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEvent {

    private Long id;
    private String numeroControle;
    private LocalDateTime dataCadastro;
    private String nomeCliente;
    private BigDecimal valorTotal;
    private Integer quantidadeTotal;
    private String codigoCliente;
    private List<ItemEvent> itens;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemEvent {
        private Long produtoId;
        private String produtoNome;
        private Integer quantidade;
        private BigDecimal valorUnitario;
    }
}*/

