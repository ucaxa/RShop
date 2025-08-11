package com.rshop.catalogo.controller;


import com.rshop.catalogo.dto.CategoriaDTO;
import com.rshop.catalogo.model.Categoria;
import com.rshop.catalogo.repository.CategoriaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodos() {
        List<CategoriaDTO> lista = categoriaRepository.findAll()
                .stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getDescricao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setDescricao(dto.getDescricao());
        Categoria salva = categoriaRepository.save(categoria);
        dto.setId(salva.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}

