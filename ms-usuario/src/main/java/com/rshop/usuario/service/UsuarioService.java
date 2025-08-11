package com.rshop.usuario.service;

import com.rshop.usuario.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO);
    void deletarUsuario(Long id);
    UsuarioDTO buscarPorId(Long id);
    List<UsuarioDTO> listarTodos();
}

