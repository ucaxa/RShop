package com.rshop.catalogo.controller;

import com.rshop.catalogo.dto.ProdutoDTO;
import com.rshop.catalogo.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO criado = produtoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> lista = produtoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO atualizado = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

