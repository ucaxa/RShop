package com.rshop.usuario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfis")
@Getter
@Setter
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    private String telefone;

    private String cpf;

    private LocalDate dataNascimento;

    // Relacionamento 1:1 com Usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacionamento 1:N com Endereco
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    // Método utilitário para adicionar endereço
    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setPerfil(this);
    }

    // Método utilitário para remover endereço
    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setPerfil(null);
    }

    // Obter endereço principal
    public Endereco getEnderecoPrincipal() {
        return enderecos.stream()
                .filter(Endereco::isPrincipal)
                .findFirst()
                .orElse(null);
    }
}
