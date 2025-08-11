package com.ms.pedido.service;


import com.ms.pedido.dto.PedidoDTO;

import java.util.List;

public interface PedidoService {
    PedidoDTO criarPedido(PedidoDTO dto);
    PedidoDTO buscarPorId(Long id);
    List<PedidoDTO> listarTodos();
}

