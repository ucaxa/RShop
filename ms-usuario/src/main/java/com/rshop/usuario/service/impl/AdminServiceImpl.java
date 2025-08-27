package com.rshop.usuario.service.impl;




import com.rshop.usuario.dto.usuario.UsuarioResponse;
import com.rshop.usuario.model.Role;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.UsuarioRepository;
import com.rshop.usuario.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioResponse> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toUsuarioResponse) // Usar método interno
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse atualizarRoleUsuario(Long usuarioId, Role novaRole) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setRole(novaRole);
        usuarioRepository.save(usuario);

        return toUsuarioResponse(usuario);
    }

    @Override
    public void desativarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setEnabled(false);
        usuarioRepository.save(usuario);
    }

    // Método interno para converter Usuario para UsuarioResponse
    private UsuarioResponse toUsuarioResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setRole(usuario.getRole().name());
        response.setEnabled(usuario.isEnabled());
        response.setDataCriacao(usuario.getDataCriacao());
        response.setDataUltimoLogin(usuario.getDataUltimoLogin());
        return response;
    }
}