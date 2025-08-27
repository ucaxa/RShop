package com.rshop.usuario.security;
import com.rshop.usuario.model.Usuario;
import com.rshop.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço personalizado para carregar detalhes do usuário durante a autenticação
 *
 * <p>Implementa a interface UserDetailsService do Spring Security para fornecer
 * informações do usuário a partir do banco de dados durante o processo de autenticação.</p>
 *
 * <p><strong>Funcionalidades:</strong></p>
 * <ul>
 *   <li>Carrega usuários por email</li>
 *   <li>Converte a entidade Usuario em UserDetails do Spring Security</li>
 *   <li>Configura roles e authorities corretamente</li>
 * </ul>
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Carrega usuário pelo email durante a autenticação
     *
     * <p>Método principal que busca um usuário no banco de dados pelo email
     * e converte para UserDetails do Spring Security, incluindo suas roles
     * e authorities.</p>
     *
     * @param email Email do usuário a ser autenticado
     * @return UserDetails com informações do usuário
     * @throws UsernameNotFoundException Se o usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole().name()) // Converte a role para formato Spring Security
                .build();
    }
}