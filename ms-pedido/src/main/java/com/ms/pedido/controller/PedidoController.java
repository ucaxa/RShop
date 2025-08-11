package com.ms.pedido.controller;



import com.ms.pedido.dto.PedidoDTO;
import com.ms.pedido.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO dto) {
        PedidoDTO criado = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
}

