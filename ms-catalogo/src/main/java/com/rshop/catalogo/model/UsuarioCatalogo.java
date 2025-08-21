package com.rshop.catalogo.model;

import com.rshop.catalogo.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidade local (não é a mesma do ms-usuario!)
@Entity
@Table(name = "usuario_catalogo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCatalogo {
    @Id
    private Long id;
    private String email;
    private String nome;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Sem referências a outras entidades do ms-usuario
}