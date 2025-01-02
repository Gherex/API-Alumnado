package com.gherex.alumnado.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Lazy
    private final JwtAuthenticationManager jwtAuthenticationManager;

    public JwtFilter(JwtUtil jwtUtil, JwtAuthenticationManager jwtAuthenticationManager) {
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Usar JwtAuthenticationManager para autenticar el usuario
            UserDetails userDetails = jwtAuthenticationManager.authenticate(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Configurar la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(
                        new JwtAuthenticationToken(userDetails, jwt, userDetails.getAuthorities()));
            }
        }

        filterChain.doFilter(request, response);
    }
}
