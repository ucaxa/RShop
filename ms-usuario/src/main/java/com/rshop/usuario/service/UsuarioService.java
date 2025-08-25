package com.rshop.usuario.service;

import com.rshop.usuario.dto.usuario.UsuarioEnderecoRequest;
import com.rshop.usuario.dto.usuario.UsuarioEnderecoResponse;
import com.rshop.usuario.dto.usuario.PerfilUpdateRequest;
import com.rshop.usuario.dto.usuario.UsuarioResponse;

import java.util.List;


public interface UsuarioService {
    UsuarioResponse criarUsuario(UsuarioResponse usuarioDTO);
    UsuarioResponse buscarPorId(Long id);
    List<UsuarioResponse> listarTodos();
    UsuarioResponse atualizarUsuario(Long id, UsuarioResponse usuarioDTO);
    void deletarUsuario(Long id);
    UsuarioResponse getUsuarioAtual();
    UsuarioResponse atualizarPerfil(PerfilUpdateRequest request);
    UsuarioEnderecoResponse adicionarEndereco(UsuarioEnderecoRequest request);
}

