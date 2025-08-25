package com.rshop.usuario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    // Relacionamento 1:1 com Perfil
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Perfil perfil;

    // Construtor padrão
    public Usuario() {}

    // Construtor para registro
    public Usuario(String email, String senha, Role role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
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
    public void ativarUsuario() {
        this.enabled = true;
        this.tokenConfirmacao = null;
        this.dataExpiracaoToken = null;
    }

    public boolean isTokenExpirado() {
        return dataExpiracaoToken != null &&
                dataExpiracaoToken.isBefore(LocalDateTime.now());
    }

    public void atualizarUltimoLogin() {
        this.dataUltimoLogin = LocalDateTime.now();
    }

    // Método para criar perfil associado
    public void criarPerfil(String nomeCompleto, String telefone, String cpf) {
        Perfil perfil = new Perfil();
        perfil.setNomeCompleto(nomeCompleto);
        perfil.setTelefone(telefone);
        perfil.setCpf(cpf);
        perfil.setUsuario(this);
        this.perfil = perfil;
    }
}