package com.rshop.usuario.service;

import com.rshop.usuario.dto.usuario.*;

import java.util.List;


public interface UsuarioService {
    UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest);
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse buscarPorEmail(String email);
    List<UsuarioResponse> listarTodos();
    UsuarioResponse atualizarUsuario(Long id, UsuarioRequest usuarioRequest);
    UsuarioResponse atualizarPerfil(Long usuarioId, PerfilUpdateRequest perfilRequest);
    void deletarUsuario(Long id);
    boolean existePorEmail(String email);
    UsuarioResponse criarUsuarioAdmin(UsuarioRequest usuarioRequest);
}

