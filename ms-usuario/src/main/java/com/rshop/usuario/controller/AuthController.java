package com.rshop.usuario.controller;

import com.rshop.usuario.dto.LoginRequest;
import com.rshop.usuario.dto.AuthResponse;
import com.rshop.usuario.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getSenha()));

            String token = jwtUtil.generateToken(authRequest.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }

    //esse controller debaixo é o correto
 /*   @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthController {

        private final JwtService jwtService;
        private final UsuarioService usuarioService;

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
            // Valida credenciais
            Usuario usuario = usuarioService.validarCredenciais(request);

            // Gera token
            String token = jwtService.generateToken(
                    usuario.getEmail(),
                    usuario.getRoles()
            );

            return ResponseEntity.ok(new LoginResponse(token));
        }
    }*/
}

