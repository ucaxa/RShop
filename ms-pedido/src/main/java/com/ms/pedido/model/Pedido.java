package com.ms.pedido.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroControle; // ex: RSHOP-20230801-0001

    private LocalDateTime dataCadastro;

    private String nomeCliente;

    private BigDecimal valorTotal;

    private Integer quantidadeTotal;

    private String codigoCliente; // opcional link com ms-usuario

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
}

