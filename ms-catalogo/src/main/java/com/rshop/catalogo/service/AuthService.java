package com.rshop.catalogo.service;


import com.rshop.catalogo.dto.LoginRequest;
import com.rshop.catalogo.dto.LoginResponse;

public interface AuthService {
    LoginResponse authenticate(LoginRequest request);
    void validateToken(String token);
}
