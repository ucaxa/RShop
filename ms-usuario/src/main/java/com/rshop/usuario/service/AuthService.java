package com.rshop.usuario.service;

import com.rshop.usuario.dto.auth.AuthResponse;
import com.rshop.usuario.dto.auth.LoginRequest;
import com.rshop.usuario.dto.auth.PasswordResetRequest;
import com.rshop.usuario.dto.auth.RegisterRequest;



public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void confirmarEmail(String token);
    void reenviarEmailConfirmacao(String email);
    void solicitarRecuperacaoSenha(String email);
    void redefinirSenha(PasswordResetRequest request);
}
