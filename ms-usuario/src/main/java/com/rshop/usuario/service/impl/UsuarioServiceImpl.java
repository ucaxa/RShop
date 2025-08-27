package com.rshop.usuario.service.impl;

import com.rshop.usuario.dto.usuario.PerfilResponse;
import com.rshop.usuario.dto.usuario.PerfilUpdateRequest;
import com.rshop.usuario.dto.usuario.UsuarioRequest;
import com.rshop.usuario.dto.usuario.UsuarioResponse;
import com.rshop.usuario.exception.UsuarioException;
import com.rshop.usuario.model.Perfil;
import com.rshop.usuario.model.Role;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.PerfilRepository;
import com.rshop.usuario.repository.UsuarioRepository;
import com.rshop.usuario.service.JwtService;
import com.rshop.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de usuários
 * Gerencia operações CRUD, perfis e integração com JWT
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new UsuarioException("Email já cadastrado: " + usuarioRequest.getEmail());
        }

        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        usuario.setRole(usuarioRequest.getRole() != null ?
                Role.valueOf(usuarioRequest.getRole()) : Role.CLIENTE);
        usuario.setEnabled(false);
        usuario.setDataCriacao(LocalDateTime.now());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return toUsuarioResponse(usuarioSalvo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UsuarioResponse criarUsuarioAdmin(UsuarioRequest usuarioRequest) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new UsuarioException("Email já cadastrado: " + usuarioRequest.getEmail());
        }

        // Criar usuário admin
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        usuario.setRole(Role.ADMIN);
        usuario.setEnabled(true);
        usuario.setDataCriacao(LocalDateTime.now());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return toUsuarioResponse(usuarioSalvo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com ID: " + id));
        return toUsuarioResponse(usuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UsuarioResponse buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com email: " + email));
        return toUsuarioResponse(usuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toUsuarioResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com ID: " + id));

        // Atualizar email se fornecido e diferente
        if (usuarioRequest.getEmail() != null &&
                !usuarioRequest.getEmail().equals(usuario.getEmail())) {

            if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
                throw new UsuarioException("Email já está em uso: " + usuarioRequest.getEmail());
            }
            usuario.setEmail(usuarioRequest.getEmail());
        }

        // Atualizar senha se fornecida
        if (usuarioRequest.getSenha() != null && !usuarioRequest.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        }

        // Atualizar role se fornecida
        if (usuarioRequest.getRole() != null) {
            usuario.setRole(Role.valueOf(usuarioRequest.getRole()));
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return toUsuarioResponse(usuarioAtualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UsuarioResponse atualizarPerfil(Long usuarioId, PerfilUpdateRequest perfilRequest) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com ID: " + usuarioId));

        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            perfil = new Perfil();
            perfil.setUsuario(usuario);
            usuario.setPerfil(perfil);
        }

        if (perfilRequest.getNomeCompleto() != null) {
            perfil.setNomeCompleto(perfilRequest.getNomeCompleto());
        }
        if (perfilRequest.getTelefone() != null) {
            perfil.setTelefone(perfilRequest.getTelefone());
        }
        if (perfilRequest.getDataNascimento() != null) {
            perfil.setDataNascimento(perfilRequest.getDataNascimento());
        }

        perfilRepository.save(perfil);
        usuarioRepository.save(usuario);

        return toUsuarioResponse(usuario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Converte entidade Usuario para UsuarioResponse
     * @param usuario Entidade usuário
     * @return Response DTO
     */
    private UsuarioResponse toUsuarioResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setRole(usuario.getRole().name());
        response.setEnabled(usuario.isEnabled());
        response.setDataCriacao(usuario.getDataCriacao());
        response.setDataUltimoLogin(usuario.getDataUltimoLogin());

        if (usuario.getPerfil() != null) {
            response.setPerfil(toPerfilResponse(usuario.getPerfil()));
        }

        return response;
    }

    /**
     * Converte entidade Perfil para PerfilResponse
     * @param perfil Entidade perfil
     * @return Response DTO
     */
    private PerfilResponse toPerfilResponse(Perfil perfil) {
        PerfilResponse response = new PerfilResponse();
        response.setNomeCompleto(perfil.getNomeCompleto());
        response.setTelefone(perfil.getTelefone());
        response.setCpf(perfil.getCpf());
        response.setDataNascimento(perfil.getDataNascimento());
        return response;
    }
}