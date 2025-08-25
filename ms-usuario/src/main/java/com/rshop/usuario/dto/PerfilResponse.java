package com.rshop.usuario.dto;


import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PerfilResponse {
    private String nomeCompleto;
    private String telefone;
    private String cpf;
    private LocalDate dataNascimento;
    private List<EnderecoResponse> enderecos;
}
