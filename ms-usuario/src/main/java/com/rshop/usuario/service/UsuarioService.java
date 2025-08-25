package com.rshop.usuario.service;

import com.rshop.usuario.dto.EnderecoRequest;
import com.rshop.usuario.dto.EnderecoResponse;
import com.rshop.usuario.dto.PerfilUpdateRequest;
import com.rshop.usuario.dto.UsuarioResponse;




public interface UsuarioService {
    UsuarioResponse getUsuarioAtual();
    UsuarioResponse atualizarPerfil(PerfilUpdateRequest request);
    EnderecoResponse adicionarEndereco(EnderecoRequest request);
}

