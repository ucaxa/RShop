package com.rshop.catalogo.service;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface JwtService {
    String resolveToken(HttpServletRequest request);
    boolean validateToken(String token);
    Authentication getAuthentication(String token);
    String generateToken(String username, List<String> roles);
}
