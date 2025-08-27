package com.rshop.usuario.dto.endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO de resposta para endereço do usuário.
 * Contém informações completas de endereçamento.
 */
@Data
@Schema(description = "DTO de resposta para endereço do usuário")
public class EnderecoResponse {

    @Schema(description = "ID único do endereço", example = "1")
    private Long id;

    @Schema(description = "CEP do endereço", example = "01001-000")
    private String cep;

    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Número do endereço", example = "123")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado do endereço (UF)", example = "SP")
    private String estado;

    @Schema(description = "Indica se é o endereço principal", example = "true")
    private boolean principal;
}