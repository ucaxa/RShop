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
 * CONFIGURAÇÃO PRINCIPAL DE SEGURANÇA DA APLICAÇÃO
 *
 * Esta classe define políticas de segurança, autenticação JWT,
 * autorização de endpoints e configurações CORS para o microserviço.
 */
@Configuration
@EnableWebSecurity          // Habilita segurança web do Spring Security
@EnableMethodSecurity       // Habilita segurança em nível de método (@PreAuthorize)
@RequiredArgsConstructor    // Gera construtor com dependências obrigatórias
public class SecurityConfig {

    // Serviço para operações JWT (geração, validação, extração)
    private final JwtService jwtService;

    // Serviço customizado para carregar detalhes do usuário do banco
    private final UsuarioDetailsService usuarioDetailsService;

    /**
     * CONFIGURAÇÃO PRINCIPAL DO FILTRO DE SEGURANÇA
     *
     * Define a cadeia de filtros de segurança, políticas de acesso,
     * configurações CORS e integração com JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔄 Configuração CORS para comunicação com frontend
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 🚫 Desabilita CSRF (não necessário para APIs stateless JWT)
                .csrf(csrf -> csrf.disable())

                // 🎯 CONFIGURAÇÃO DE AUTORIZAÇÃO DE ENDPOINTS
                .authorizeHttpRequests(authz -> authz
                        // ✅ ENDPOINTS PÚBLICOS (acesso liberado)
                        .requestMatchers("/auth/**").permitAll()                   // Autenticação
                        .requestMatchers("/api/usuarios/verificar-email/**").permitAll() // Verificação email

                        // 📚 DOCUMENTAÇÃO SWAGGER (acesso liberado para desenvolvimento)
                        .requestMatchers(
                                "/swagger-ui.html", "/swagger-ui/**", "/swagger-ui/index.html",
                                "/v3/api-docs", "/v3/api-docs/**", "/v2/api-docs", "/v2/api-docs/**",
                                "/swagger-resources", "/swagger-resources/**",
                                "/configuration/ui", "/configuration/security",
                                "/webjars/**"
                        ).permitAll()

                        // 👮‍♂️ ENDPOINTS ADMINISTRATIVOS (acesso restrito)
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyRole("ADMIN", "MANAGER")    // Leitura
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")                 // Criação
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")                  // Atualização
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")               // Exclusão

                        // 👥 GESTÃO DE USUÁRIOS (acesso restrito)
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                        // 🛒 ENDPOINTS DE NEGÓCIO (acesso autenticado)
                        .requestMatchers("/api/pedidos/**").authenticated()        // Pedidos
                        .requestMatchers("/api/pagamentos/**").authenticated()     // Pagamentos

                        // 🏥 HEALTH CHECKS (acesso liberado para monitoramento)
                        .requestMatchers("/actuator/health", "/health").permitAll()

                        // 🔒 QUALQUER OUTRO ENDPOINT exige autenticação
                        .anyRequest().authenticated()
                )

                // 💾 CONFIGURAÇÃO DE SESSÃO (stateless = não guarda estado no servidor)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔐 PROVEDOR DE AUTENTICAÇÃO customizado
                .authenticationProvider(authenticationProvider())

                // 🎪 ADICIONA FILTRO JWT antes do filtro de autenticação padrão
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * FILTRO JWT PERSONALIZADO
     *
     * Responsável por:
     * - Extrair token JWT do header Authorization
     * - Validar token
     * - Carregar UserDetails do usuário
     * - Configurar SecurityContext com autenticação
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, usuarioDetailsService);
    }

    /**
     * PROVEDOR DE AUTENTICAÇÃO DAO
     *
     * Integra Spring Security com nosso UserDetailsService customizado
     * e encoder de senha BCrypt.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService);  // Nosso service customizado
        authProvider.setPasswordEncoder(passwordEncoder());         // Encoder BCrypt
        return authProvider;
    }

    /**
     * GERENCIADOR DE AUTENTICAÇÃO
     *
     * Bean padrão do Spring Security para gerenciar processos de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * ENCODER DE SENHAS
     *
     * Usa BCrypt para hashing seguro de senhas.
     * Strength 10 = 2^10 iterações (balance entre segurança e performance).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CONFIGURAÇÃO CORS (Cross-Origin Resource Sharing)
     *
     * Permite comunicação segura entre frontend e backend em origens diferentes.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Frontend URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type")); // Headers visíveis para front
        configuration.setAllowCredentials(true);  // Permite cookies/auth credentials
        configuration.setMaxAge(3600L);           // Cache de pré-flight requests por 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas as rotas
        return source;
    }
}