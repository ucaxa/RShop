package com.rshop.usuario.exception;

/**
 * Exceção personalizada para operações relacionadas a usuários
 * Fornece mensagens de erro mais específicas para o domínio de usuários
 */
public class UsuarioException extends RuntimeException {

    /**
     * Cria uma nova exceção de usuário com mensagem específica
     * @param message Mensagem de erro descriptiva
     */
    public UsuarioException(String message) {
        super(message);
    }

    /**
     * Cria uma nova exceção de usuário com mensagem e causa
     * @param message Mensagem de erro descriptiva
     * @param cause Exceção original que causou este erro
     */
    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
