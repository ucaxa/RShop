package com.rshop.usuario.dto.usuario;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UsuarioEnderecoRequest {
    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    private boolean principal = false;
}
