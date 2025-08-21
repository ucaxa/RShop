package com.rshop.catalogo.controller;

import com.rshop.catalogo.dto.LoginRequest;
import com.rshop.catalogo.dto.LoginResponse;
import com.rshop.catalogo.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }

    @GetMapping("/validate")
    public void validateToken(@RequestHeader("Authorization") String token) {
        authService.validateToken(token.replace("Bearer ", ""));
    }
}
