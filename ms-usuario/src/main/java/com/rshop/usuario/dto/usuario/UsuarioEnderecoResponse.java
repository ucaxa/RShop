package com.rshop.usuario.dto.usuario;


import lombok.Data;

@Data
public class UsuarioEnderecoResponse {
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private boolean principal;
}
