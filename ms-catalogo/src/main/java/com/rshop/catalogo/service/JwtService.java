package com.rshop.catalogo.service;


import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface JwtService {
    //String generateToken(String username, String role); // Adicionado
    String resolveToken(HttpServletRequest request);
    boolean validateToken(String token);
    Authentication getAuthentication(String token);
    String generateToken(String username, String role) ;
}
