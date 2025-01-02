package com.gherex.alumnado.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal; // El usuario autenticado
    private final String token; // El token JWT

    public JwtAuthenticationToken(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true); // Este token ya está autenticado
    }

    public JwtAuthenticationToken(UserDetails principal) {
        super(null);
        this.principal = principal;
        this.token = null;
        setAuthenticated(false); // Token no autenticado
    }

    @Override
    public Object getCredentials() {
        return token; // El token JWT como credencial
    }

    @Override
    public Object getPrincipal() {
        return principal; // El usuario autenticado
    }
}
