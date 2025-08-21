package com.rshop.catalogo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;          // Token JWT gerado
    private String refreshToken;   // Token para renovação (opcional)
    private Long expiresIn;        // Tempo de expiração em segundos
    private String tokenType;      // Tipo do token (ex: "Bearer")
    private UsuarioResponse  usuarioResponse;     // Dados do usuário autenticado

    // Construtor padrão (necessário para frameworks como Jackson)
 /*   public AuthResponse() {
    }

    // Construtor com campos principais
    public AuthResponse(String token, String refreshToken, Long expiresIn, String tokenType, UsuarioResponse user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.usuarioResponse = user;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UsuarioResponse getUser() {
        return usuarioResponse;
    }

    public void setUser(UsuarioResponse user) {
        this.usuarioResponse = user;
    }

    // Método toString() para facilitar a visualização
    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", tokenType='" + tokenType + '\'' +
                ", user=" + usuarioResponse +
                '}';
    }*/
}