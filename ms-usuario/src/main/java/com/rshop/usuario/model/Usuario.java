package com.rshop.usuario.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entidade que representa um usuário do sistema.
 * Contém informações de autenticação, dados pessoais e endereços.
 * Implementa UserDetails para integração com Spring Security.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CLIENTE;

    @Column(nullable = false)
    private boolean enabled = false; // Confirmação de email

    private String tokenConfirmacao;

    private LocalDateTime dataExpiracaoToken;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataUltimoLogin;

    // Dados pessoais (antigamente na classe Perfil)
    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    private String telefone;

    @Column(unique = true)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    // Relacionamento 1:N com Endereco
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    /**
     * Construtor padrão.
     */
    public Usuario() {}

    /**
     * Construtor para criação de novo usuário.
     *
     * @param email Email do usuário (deve ser único)
     * @param senha Senha criptografada do usuário
     * @param role Papel do usuário no sistema
     * @param nomeCompleto Nome completo do usuário
     */
    public Usuario(String email, String senha, Role role, String nomeCompleto) {
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.nomeCompleto = nomeCompleto;
        this.enabled = false;
    }

    // Métodos do UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Métodos utilitários
    /**
     * Ativa a conta do usuário e limpa os tokens de confirmação.
     */
    public void ativarUsuario() {
        this.enabled = true;
        this.tokenConfirmacao = null;
        this.dataExpiracaoToken = null;
    }

    /**
     * Verifica se o token de confirmação está expirado.
     *
     * @return true se o token estiver expirado, false caso contrário
     */
    public boolean isTokenExpirado() {
        return dataExpiracaoToken != null &&
                dataExpiracaoToken.isBefore(LocalDateTime.now());
    }

    /**
     * Atualiza a data do último login para o momento atual.
     */
    public void atualizarUltimoLogin() {
        this.dataUltimoLogin = LocalDateTime.now();
    }

    /**
     * Adiciona um endereço à lista de endereços do usuário.
     *
     * @param endereco Endereço a ser adicionado
     */
    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setUsuario(this);
    }

    /**
     * Remove um endereço da lista de endereços do usuário.
     *
     * @param endereco Endereço a ser removido
     */
    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setUsuario(null);
    }

    /**
     * Retorna o endereço principal do usuário.
     *
     * @return Endereço marcado como principal, ou null se não houver
     */
    public Endereco getEnderecoPrincipal() {
        return enderecos.stream()
                .filter(Endereco::isPrincipal)
                .findFirst()
                .orElse(null);
    }

    /**
     * Verifica se o usuário possui algum endereço cadastrado.
     *
     * @return true se o usuário tiver pelo menos um endereço, false caso contrário
     */
    public boolean possuiEnderecos() {
        return enderecos != null && !enderecos.isEmpty();
    }

    /**
     * Retorna a idade do usuário com base na data de nascimento.
     *
     * @return Idade em anos, ou null se data de nascimento não estiver definida
     */
    public Integer getIdade() {
        if (dataNascimento == null) {
            return null;
        }
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }
}