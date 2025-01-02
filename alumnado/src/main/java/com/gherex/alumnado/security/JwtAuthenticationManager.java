package com.gherex.alumnado.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager {

    @Lazy
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationManager(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetails authenticate(String username) {
        // Usamos el UserDetailsService para cargar el usuario por nombre
        return userDetailsService.loadUserByUsername(username);
    }
}
