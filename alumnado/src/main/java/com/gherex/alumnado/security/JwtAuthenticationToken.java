package com.gherex.alumnado.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal; // Información del usuario (e.g., nombre de usuario, roles)
    private final String token; // El token JWT

    public JwtAuthenticationToken(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        this.setAuthenticated(true); // Marcamos este token como autenticado
    }

    @Override
    public Object getCredentials() {
        return token; // El JWT se considera como las credenciales
    }

    @Override
    public Object getPrincipal() {
        return principal; // La información del usuario autenticado
    }
}
