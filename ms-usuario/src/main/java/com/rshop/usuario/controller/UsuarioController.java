package com.rshop.usuario.controller;
import com.rshop.usuario.dto.usuario.*;
import com.rshop.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER PARA GESTÃO DE USUÁRIOS
 *
 * Responsável por operações CRUD de usuários. Endpoints protegidos
 * com autorização baseada em roles utilizando @PreAuthorize.
 *
 * @Security Hierarchy:
 * - ADMIN: Operações completas de CRUD
 * - MANAGER: Apenas operações de leitura
 * - CLIENTE: Apenas acesso aos próprios dados
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * CRIA UM NOVO USUÁRIO (APENAS ADMIN)
     * Endpoint para criação de usuários comuns (role CLIENTE)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> criarUsuario(
            @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioCriado = usuarioService.criarUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    /**
     * CRIA USUÁRIO COM ROLE ESPECÍFICA (APENAS ADMIN)
     * Endpoint administrativo para criar usuários com qualquer role
     */
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> criarUsuarioComRole(
            @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioCriado = usuarioService.criarUsuarioAdmin(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    /**
     * BUSCA USUÁRIO POR ID (ADMIN/MANAGER OU PRÓPRIO USUÁRIO)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or @securityUtils.isOwnProfile(#id)")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * BUSCA USUÁRIO POR EMAIL (APENAS ADMIN/MANAGER)
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<UsuarioResponse> buscarPorEmail(@PathVariable String email) {
        UsuarioResponse usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    /**
     * LISTA TODOS OS USUÁRIOS (APENAS ADMIN/MANAGER)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * ATUALIZA USUÁRIO (ADMIN OU PRÓPRIO USUÁRIO)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isOwnProfile(#id)")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioRequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    /**
     * ATUALIZA PERFIL (ADMIN OU PRÓPRIO USUÁRIO)
     */
    @PutMapping("/{id}/perfil")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isOwnProfile(#id)")
    public ResponseEntity<UsuarioResponse> atualizarPerfil(
            @PathVariable Long id,
            @Valid @RequestBody PerfilUpdateRequest perfilRequest) {
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarPerfil(id, perfilRequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    /**
     * DELETA USUÁRIO (APENAS ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * VERIFICA DISPONIBILIDADE DE EMAIL (PÚBLICO)
     */
    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) {
        boolean existe = usuarioService.existePorEmail(email);
        return ResponseEntity.ok(existe);
    }
}