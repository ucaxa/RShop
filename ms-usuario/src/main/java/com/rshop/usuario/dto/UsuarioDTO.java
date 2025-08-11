package com.rshop.usuario.dto;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
}
