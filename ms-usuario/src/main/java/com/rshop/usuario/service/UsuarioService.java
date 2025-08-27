package com.rshop.usuario.service;

import com.rshop.usuario.dto.usuario.*;
import com.rshop.usuario.model.Usuario;

import java.util.List;


public interface UsuarioService {
    UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest);
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse buscarPorEmail(String email);
    List<UsuarioResponse> listarTodos();
    UsuarioResponse atualizarUsuario(Long id, UsuarioUpdateRequest usuarioUpdateRequest);
    void deletarUsuario(Long id);
    boolean existePorEmail(String email);
    UsuarioResponse criarUsuarioAdmin(UsuarioRequest usuarioRequest);
}

