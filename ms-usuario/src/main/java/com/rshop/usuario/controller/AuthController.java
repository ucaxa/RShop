package com.rshop.usuario.controller;

import com.rshop.usuario.dto.auth.AuthResponse;
import com.rshop.usuario.dto.auth.LoginRequest;
import com.rshop.usuario.dto.auth.PasswordResetRequest;
import com.rshop.usuario.dto.auth.RegisterRequest;
import com.rshop.usuario.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/confirmar-email")
    public ResponseEntity<String> confirmarEmail(@RequestParam String token) {
        authService.confirmarEmail(token);
        return ResponseEntity.ok("Email confirmado com sucesso!");
    }

    @PostMapping("/reenviar-confirmacao")
    public ResponseEntity<String> reenviarConfirmacao(@RequestParam String email) {
        authService.reenviarEmailConfirmacao(email);
        return ResponseEntity.ok("Email de confirmação reenviado!");
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> solicitarRecuperacaoSenha(@RequestParam String email) {
        authService.solicitarRecuperacaoSenha(email);
        return ResponseEntity.ok("Email de recuperação enviado!");
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@Valid @RequestBody PasswordResetRequest request) {
        authService.redefinirSenha(request);
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Auth service is running!");
    }
}