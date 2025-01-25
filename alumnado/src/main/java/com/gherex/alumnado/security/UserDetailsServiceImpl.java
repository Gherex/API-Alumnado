package com.gherex.alumnado.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Leer la contraseña hash desde application.properties
    @Value("${app.password.hash}")
    private String adminPasswordHash;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Verificar si el usuario es "admin"
        if ("admin".equals(username)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username("admin")
                    .password(adminPasswordHash) // Usar el hash configurado
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
