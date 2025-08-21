package com.rshop.catalogo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginRequest {
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        private final String email;

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        private final String senha;

        public LoginRequest(String email, String senha) {
                this.email = email;
                this.senha = senha;
        }

        // Getters
        public String getEmail() {
                return email;
        }

        public String getSenha() {
                return senha;
        }
}
