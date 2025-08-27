package com.rshop.usuario.service.impl;

import com.rshop.usuario.dto.usuario.UsuarioRequest;
import com.rshop.usuario.dto.usuario.UsuarioResponse;
import com.rshop.usuario.dto.usuario.UsuarioUpdateRequest;
import com.rshop.usuario.exception.UsuarioException;
import com.rshop.usuario.model.Role;
import com.rshop.usuario.model.Usuario;
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
        usuario.setDataNascimento(usuarioRequest.getDataNascimento());

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
        usuario.setDataNascimento(usuarioRequest.getDataNascimento());

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
    public UsuarioResponse atualizarUsuario(Long id, UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com ID: " + id));

        // Atualizar email se fornecido e diferente
        if (usuarioUpdateRequest.getEmail() != null &&
                !usuarioUpdateRequest.getEmail().equals(usuario.getEmail())) {

            if (usuarioRepository.existsByEmail(usuarioUpdateRequest.getEmail())) {
                throw new UsuarioException("Email já está em uso: " + usuarioUpdateRequest.getEmail());
            }
            usuario.setEmail(usuarioUpdateRequest.getEmail());
        }

        // Atualizar senha se fornecida
        if (usuarioUpdateRequest.getSenha() != null && !usuarioUpdateRequest.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuarioUpdateRequest.getSenha()));
        }

        // Atualizar role se fornecida
        if (usuarioUpdateRequest.getRole() != null) {
            usuario.setRole(Role.valueOf(usuarioUpdateRequest.getRole()));
        }

        if (usuarioUpdateRequest.getNomeCompleto() != null) {
            usuario.setNomeCompleto(usuarioUpdateRequest.getNomeCompleto());
        }
        if (usuarioUpdateRequest.getTelefone() != null) {
            usuario.setTelefone(usuarioUpdateRequest.getTelefone());
        }
        if (usuarioUpdateRequest.getDataNascimento() != null) {
            usuario.setDataNascimento(usuarioUpdateRequest.getDataNascimento());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return toUsuarioResponse(usuarioAtualizado);
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

        return response;
    }


}