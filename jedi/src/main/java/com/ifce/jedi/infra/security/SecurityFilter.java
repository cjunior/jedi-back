package com.ifce.jedi.infra.security;

import com.ifce.jedi.repository.UserRepository;
import com.ifce.jedi.service.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorizationService authorizationService; // seu UserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Pega o token do cabeçalho Authorization
        String token = recoverToken(request);

        if (token != null) {
            // Extrai o login (subject) do token
            String login = tokenService.getSubject(token);

            if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Carrega o usuário do banco
                UserDetails user = authorizationService.loadUserByUsername(login);

                // Cria autenticação com as authorities corretas
                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                // Registra no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continua o filtro
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
