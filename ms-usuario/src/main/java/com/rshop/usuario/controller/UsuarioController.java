package com.rshop.usuario.controller;

import com.rshop.usuario.dto.usuario.PerfilUpdateRequest;
import com.rshop.usuario.dto.usuario.UsuarioRequest;
import com.rshop.usuario.dto.usuario.UsuarioResponse;
import com.rshop.usuario.service.UsuarioService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UsuarioResponse> criarUsuario(
            @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioCriado = usuarioService.criarUsuario(usuarioRequest);
        return ResponseEntity.ok(usuarioCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> buscarPorEmail(@PathVariable String email) {
        UsuarioResponse usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioRequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PutMapping("/{id}/perfil")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(
            @PathVariable Long id,
            @Valid @RequestBody PerfilUpdateRequest perfilRequest) {
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarPerfil(id, perfilRequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) {
        boolean existe = usuarioService.existePorEmail(email);
        return ResponseEntity.ok(existe);
    }
}