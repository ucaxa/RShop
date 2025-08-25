package com.rshop.usuario.service;

import com.rshop.usuario.dto.AuthResponse;
import com.rshop.usuario.dto.LoginRequest;
import com.rshop.usuario.dto.PasswordResetRequest;
import com.rshop.usuario.dto.RegisterRequest;



public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void confirmarEmail(String token);
    void reenviarEmailConfirmacao(String email);
    void solicitarRecuperacaoSenha(String email);
    void redefinirSenha(PasswordResetRequest request);
}
