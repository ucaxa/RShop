package com.rshop.usuario.service.impl;



import com.rshop.usuario.dto.UsuarioDTO;
import com.rshop.usuario.exception.ResourceNotFoundException;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.UsuarioRepository;
import com.rshop.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        return modelMapper.map(salvo, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        // Atualizar senha se fornecida
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }
        Usuario atualizado = usuarioRepository.save(usuario);
        return modelMapper.map(atualizado, UsuarioDTO.class);
    }

    @Override
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(u -> modelMapper.map(u, UsuarioDTO.class))
                .collect(Collectors.toList());
    }
}

