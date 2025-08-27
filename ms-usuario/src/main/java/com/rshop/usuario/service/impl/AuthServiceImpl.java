package com.rshop.usuario.service.impl;


import com.rshop.usuario.dto.auth.AuthResponse;
import com.rshop.usuario.dto.auth.LoginRequest;
import com.rshop.usuario.dto.auth.PasswordResetRequest;
import com.rshop.usuario.dto.auth.RegisterRequest;
import com.rshop.usuario.model.Role;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.UsuarioRepository;
import com.rshop.usuario.service.AuthService;
import com.rshop.usuario.service.EmailService;
import com.rshop.usuario.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Role.CLIENTE);
        usuario.setTokenConfirmacao(UUID.randomUUID().toString());
        usuario.setDataExpiracaoToken(LocalDateTime.now().plusHours(24));
        //dados que estavam no perfil
        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setTelefone(request.getTelefone());
        usuario.setCpf(request.getCpf());


       /* usuario.criarPerfil(
                request.getNomeCompleto(),
                request.getTelefone(),
                request.getCpf()
        );*/

        usuarioRepository.save(usuario);
        emailService.enviarEmailConfirmacao(usuario.getEmail(), usuario.getTokenConfirmacao());

        return new AuthResponse(
                null,
                usuario.getEmail(),
                usuario.getRole().name()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.isEnabled()) {
            throw new RuntimeException("Email não confirmado");
        }

        usuario.atualizarUltimoLogin();
        usuarioRepository.save(usuario);

        return new AuthResponse(
                jwtService.generateToken(usuario),
                usuario.getEmail(),
                usuario.getRole().name()
        );
    }

    @Override
    @Transactional
    public void confirmarEmail(String token) {
        Usuario usuario = usuarioRepository.findByTokenConfirmacao(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (usuario.isTokenExpirado()) {
            throw new RuntimeException("Token expirado");
        }

        usuario.ativarUsuario();
        usuarioRepository.save(usuario);
        emailService.enviarEmailBemVindo(usuario.getEmail(), usuario.getNomeCompleto());
    }

    @Override
    public void reenviarEmailConfirmacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));

        if (usuario.isEnabled()) {
            throw new RuntimeException("Email já confirmado");
        }

        usuario.setTokenConfirmacao(UUID.randomUUID().toString());
        usuario.setDataExpiracaoToken(LocalDateTime.now().plusHours(24));
        usuarioRepository.save(usuario);

        emailService.enviarEmailConfirmacao(email, usuario.getTokenConfirmacao());
    }

    @Override
    public void solicitarRecuperacaoSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));

        String token = UUID.randomUUID().toString();
        usuario.setTokenConfirmacao(token);
        usuario.setDataExpiracaoToken(LocalDateTime.now().plusHours(2));
        usuarioRepository.save(usuario);

        emailService.enviarEmailRecuperacaoSenha(email, token);
    }

    @Override
    @Transactional
    public void redefinirSenha(PasswordResetRequest request) {
        Usuario usuario = usuarioRepository.findByTokenConfirmacao(request.getToken())
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (usuario.isTokenExpirado()) {
            throw new RuntimeException("Token expirado");
        }

        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        usuario.setTokenConfirmacao(null);
        usuario.setDataExpiracaoToken(null);
        usuarioRepository.save(usuario);
    }
}