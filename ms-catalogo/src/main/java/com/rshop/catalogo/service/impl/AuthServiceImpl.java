package com.rshop.catalogo.service.impl;


// src/main/java/com/rshop/catalogo/service/AuthServiceImpl.java


import com.rshop.catalogo.dto.AuthResponse;
import com.rshop.catalogo.dto.LoginRequest;
import com.rshop.catalogo.dto.LoginResponse;
import com.rshop.catalogo.dto.UsuarioResponse;
import com.rshop.catalogo.enums.UserRole;
import com.rshop.catalogo.model.UsuarioCatalogo;
import com.rshop.catalogo.repository.UsuarioCatalogoRepository;
import com.rshop.catalogo.service.AuthService;
import com.rshop.catalogo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioCatalogoRepository usuarioRepository;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        UsuarioCatalogo usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));

        if (usuario.getRole() == UserRole.INATIVO) {
            throw new SecurityException("Usuário desativado");
        }

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getRole().name());

        return new AuthResponse(
                new LoginResponse(token, "Bearer", 3600),
                new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole().name())
        );
    }

    @Override
    public void validateToken(String token) {

    }
}