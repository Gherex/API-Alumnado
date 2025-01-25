package com.gherex.alumnado.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationManager(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetails authenticate(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}
