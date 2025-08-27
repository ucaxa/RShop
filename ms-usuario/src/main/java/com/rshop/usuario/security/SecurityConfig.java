package com.rshop.usuario.security;
import com.rshop.usuario.service.JwtService;
import com.rshop.usuario.service.impl.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuração principal de segurança da aplicação
 *
 * <p>Responsável por configurar políticas de segurança, autenticação JWT,
 * autorização de endpoints e CORS para o microserviço de usuários.</p>
 *
 * <p><strong>Hierarquia de Roles:</strong></p>
 * <ul>
 *   <li><code>ROLE_ADMIN</code>: Acesso completo ao sistema</li>
 *   <li><code>ROLE_MANAGER</code>: Acesso de leitura administrativo</li>
 *   <li><code>ROLE_CLIENTE</code>: Acesso apenas aos próprios recursos</li>
 * </ul>
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    /**
     * Configuração principal da cadeia de filtros de segurança
     *
     * <p>Define políticas de acesso, CORS, autenticação e autorização para todos os endpoints
     * da aplicação. Configura a aplicação para ser stateless com autenticação JWT.</p>
     *
     * @param http Configuração HTTP do Spring Security
     * @return SecurityFilterChain configurado
     * @throws Exception Se ocorrer erro na configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // 🔓 ENDPOINTS PÚBLICOS
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/usuarios/verificar-email/**").permitAll()

                        // 📚 DOCUMENTAÇÃO
                        .requestMatchers(
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                "/v2/api-docs/**", "/swagger-resources/**", "/webjars/**",
                                "/configuration/ui", "/configuration/security"
                        ).permitAll()

                        // 👮‍♂️ ENDPOINTS ADMINISTRATIVOS
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasAuthority("ROLE_ADMIN")

                        // 👥 GESTÃO DE USUÁRIOS
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")

                        // 🛒 ENDPOINTS DE NEGÓCIO
                        .requestMatchers("/api/pedidos/**").authenticated()
                        .requestMatchers("/api/pagamentos/**").authenticated()

                        // 🏥 HEALTH CHECKS
                        .requestMatchers("/actuator/health", "/health").permitAll()

                        // 🔒 TODAS OUTRAS REQUISIÇÕES
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Filtro de autenticação JWT
     *
     * <p>Filtro personalizado para processar tokens JWT em cada requisição
     * e configurar o contexto de segurança do Spring.</p>
     *
     * @return JwtAuthenticationFilter configurado
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, usuarioDetailsService);
    }

    /**
     * Provedor de autenticação personalizado
     *
     * <p>Configura o provedor de autenticação para usar o UserDetailsService
     * personalizado e o encoder de senha BCrypt.</p>
     *
     * @return AuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Gerenciador de autenticação
     *
     * <p>Bean responsável por gerenciar o processo de autenticação
     * na aplicação.</p>
     *
     * @param config Configuração de autenticação do Spring
     * @return AuthenticationManager configurado
     * @throws Exception Se ocorrer erro na configuração
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Encoder de senha BCrypt
     *
     * <p>Bean responsável por codificar e verificar senhas
     * usando o algoritmo BCrypt.</p>
     *
     * @return PasswordEncoder com algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuração CORS para comunicação com frontend
     *
     * <p>Permite requisições cross-origin do frontend Angular
     * e outros clientes autorizados.</p>
     *
     * @return CorsConfigurationSource configurado
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}