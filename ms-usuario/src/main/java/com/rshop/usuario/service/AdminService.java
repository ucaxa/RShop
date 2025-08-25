package com.rshop.usuario.service;


import com.rshop.usuario.dto.UsuarioResponse;
import com.rshop.usuario.model.Role;

import java.util.List;

public interface AdminService {
    List<UsuarioResponse> listarTodosUsuarios();
    UsuarioResponse atualizarRoleUsuario(Long usuarioId, Role novaRole);
    void desativarUsuario(Long usuarioId);
}
