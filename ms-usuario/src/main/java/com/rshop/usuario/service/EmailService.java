package com.rshop.usuario.service;


public interface EmailService {
    void enviarEmailConfirmacao(String email, String token);
    void enviarEmailRecuperacaoSenha(String email, String token);
    void enviarEmailBemVindo(String email, String nome);
}
