package com.rshop.usuario.service.impl;


import com.rshop.usuario.dto.UsuarioResponse;
import com.rshop.usuario.model.Role;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.UsuarioRepository;
import com.rshop.usuario.service.AdminService;
import com.rshop.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Override
    public List<UsuarioResponse> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioService::toUsuarioResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse atualizarRoleUsuario(Long usuarioId, Role novaRole) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setRole(novaRole);
        usuarioRepository.save(usuario);

        return usuarioService.toUsuarioResponse(usuario);
    }

    @Override
    public void desativarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setEnabled(false);
        usuarioRepository.save(usuario);
    }
}