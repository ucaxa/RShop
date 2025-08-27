package com.rshop.usuario.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um endereço de um usuário.
 * Um usuário pode ter múltiplos endereços, sendo um deles o principal.
 */
@Entity
@Table(name = "enderecos")
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 200)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false)
    private boolean principal = false;

    // Relacionamento com Usuario (muitos endereços para um usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Construtor padrão.
     */
    public Endereco() {}

    /**
     * Construtor com parâmetros básicos.
     *
     * @param cep CEP do endereço
     * @param logradouro Nome da rua/avenida
     * @param numero Número do endereço
     * @param bairro Bairro
     * @param cidade Cidade
     * @param estado Estado (UF)
     * @param usuario Usuário proprietário do endereço
     */
    public Endereco(String cep, String logradouro, String numero, String bairro,
                    String cidade, String estado, Usuario usuario) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.usuario = usuario;
    }

    /**
     * Retorna uma representação formatada do endereço.
     *
     * @return String com o endereço formatado
     */
    public String getEnderecoFormatado() {
        return String.format("%s, %s%s - %s, %s - %s",
                logradouro,
                numero,
                complemento != null ? " (" + complemento + ")" : "",
                bairro,
                cidade,
                estado);
    }

    /**
     * Marca este endereço como principal e desmarca outros endereços do usuário.
     */
    public void marcarComoPrincipal() {
        // Desmarca outros endereços principais do usuário
        if (usuario != null && usuario.getEnderecos() != null) {
            usuario.getEnderecos().forEach(end -> end.setPrincipal(false));
        }
        this.principal = true;
    }

    /**
     * Verifica se o endereço está completo (todos os campos obrigatórios preenchidos).
     *
     * @return true se o endereço estiver completo, false caso contrário
     */
    public boolean isCompleto() {
        return cep != null && !cep.isEmpty() &&
                logradouro != null && !logradouro.isEmpty() &&
                numero != null && !numero.isEmpty() &&
                bairro != null && !bairro.isEmpty() &&
                cidade != null && !cidade.isEmpty() &&
                estado != null && !estado.isEmpty();
    }
}