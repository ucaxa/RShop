package com.rshop.usuario.dto.endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de requisição para cadastro de endereço do usuário.
 * Utilizado para criar ou atualizar endereços.
 */
@Data
@Schema(description = "DTO de requisição para endereço do usuário")
public class EnderecoRequest {

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
    @Schema(description = "CEP do endereço", example = "01001-000")
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    @Schema(description = "Número do endereço", example = "123")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatório")
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ser a sigla com 2 caracteres")
    @Pattern(regexp = "[A-Z]{2}", message = "Estado deve ser uma sigla válida (ex: SP, RJ)")
    @Schema(description = "Estado do endereço (UF)", example = "SP")
    private String estado;

    @Schema(description = "Indica se é o endereço principal", example = "true")
    private boolean principal = false;
}
