package com.rshop.usuario.security;
import com.rshop.usuario.service.JwtService;
import com.rshop.usuario.service.impl.UsuarioDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticação JWT para processar tokens em cada requisição
 *
 * <p>Filtro que intercepta todas as requisições HTTP para verificar a presença
 * de um token JWT válido no header Authorization. Se válido, configura a autenticação
 * no contexto de segurança do Spring.</p>
 *
 * <p><strong>Fluxo de autenticação:</strong></p>
 * <ol>
 *   <li>Verifica header Authorization com prefixo "Bearer "</li>
 *   <li>Extrai e valida o token JWT</li>
 *   <li>Carrega UserDetails a partir do token válido</li>
 *   <li>Configura autenticação no SecurityContext</li>
 * </ol>
 *
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    /**
     * Processa cada requisição HTTP para autenticação JWT
     *
     * <p>Método principal do filtro que verifica a presença de token JWT,
     * valida-o e configura a autenticação no contexto de segurança.</p>
     *
     * @param request Requisição HTTP
     * @param response Resposta HTTP
     * @param filterChain Cadeia de filtros
     * @throws ServletException Se ocorrer erro no servlet
     * @throws IOException Se ocorrer erro de I/O
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verificar se o header Authorization existe e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrair o token JWT (remover "Bearer " do início)
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            // Verificar se já não existe autenticação no contexto
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.usuarioDetailsService.loadUserByUsername(userEmail);

                // Validar o token JWT
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Adicionar detalhes da requisição
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Configurar a autenticação no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao processar token JWT", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado");
            return;
        }

        // Continuar o filtro
        filterChain.doFilter(request, response);
    }
}