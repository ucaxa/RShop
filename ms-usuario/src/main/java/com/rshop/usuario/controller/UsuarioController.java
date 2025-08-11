package com.rshop.usuario.controller;

import com.rshop.usuario.dto.UsuarioDTO;
import com.rshop.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO criado = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.ok(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> lista = usuarioService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO atualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

