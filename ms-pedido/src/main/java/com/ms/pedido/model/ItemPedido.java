package com.ms.pedido.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produtoId;

    private String produtoNome;

    private BigDecimal valorUnitario;

    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}

