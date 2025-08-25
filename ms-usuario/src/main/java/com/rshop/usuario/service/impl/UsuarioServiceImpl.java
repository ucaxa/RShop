package com.rshop.usuario.service.impl;


import com.rshop.usuario.dto.usuario.*;
import com.rshop.usuario.model.Endereco;
import com.rshop.usuario.model.Perfil;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.PerfilRepository;
import com.rshop.usuario.repository.UsuarioRepository;
import com.rshop.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;


    @Override
    public UsuarioResponse getUsuarioAtual() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return toUsuarioResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponse atualizarPerfil(PerfilUpdateRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            perfil = new Perfil();
            perfil.setUsuario(usuario);
            usuario.setPerfil(perfil);
        }

        if (request.getNomeCompleto() != null) {
            perfil.setNomeCompleto(request.getNomeCompleto());
        }
        if (request.getTelefone() != null) {
            perfil.setTelefone(request.getTelefone());
        }
        if (request.getDataNascimento() != null) {
            perfil.setDataNascimento(request.getDataNascimento());
        }

        perfilRepository.save(perfil);
        usuarioRepository.save(usuario);

        return toUsuarioResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioEnderecoResponse adicionarEndereco(UsuarioEnderecoRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            throw new RuntimeException("Perfil não encontrado");
        }

        Endereco endereco = new Endereco();
        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        endereco.setPrincipal(request.isPrincipal());

        if (request.isPrincipal()) {
            perfil.getEnderecos().forEach(e -> e.setPrincipal(false));
        }

        perfil.adicionarEndereco(endereco);
        perfilRepository.save(perfil);

        return toEnderecoResponse(endereco);
    }

    // Métodos auxiliares de conversão
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

    private PerfilResponse toPerfilResponse(Perfil perfil) {
        PerfilResponse response = new PerfilResponse();
        response.setNomeCompleto(perfil.getNomeCompleto());
        response.setTelefone(perfil.getTelefone());
        response.setCpf(perfil.getCpf());
        response.setDataNascimento(perfil.getDataNascimento());

        if (perfil.getEnderecos() != null) {
            response.setEnderecos(perfil.getEnderecos().stream()
                    .map(this::toEnderecoResponse)
                    .collect(Collectors.toList()));
        }

        return response;
    }

    private UsuarioEnderecoResponse toEnderecoResponse(Endereco endereco) {
        UsuarioEnderecoResponse response = new UsuarioEnderecoResponse();
        response.setId(endereco.getId());
        response.setCep(endereco.getCep());
        response.setLogradouro(endereco.getLogradouro());
        response.setNumero(endereco.getNumero());
        response.setComplemento(endereco.getComplemento());
        response.setBairro(endereco.getBairro());
        response.setCidade(endereco.getCidade());
        response.setEstado(endereco.getEstado());
        response.setPrincipal(endereco.isPrincipal());
        return response;
    }
}