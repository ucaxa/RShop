package com.rshop.usuario.service;


import com.rshop.usuario.model.Usuario;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String generateToken(Usuario usuario);
    boolean isTokenValid(String token, UserDetails userDetails);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);
}