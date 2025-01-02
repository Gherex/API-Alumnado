package com.gherex.alumnado.security;

import com.gherex.alumnado.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF si estás desarrollando una API RESTful
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas accesibles para todos (sin autenticación)
                        .requestMatchers("/auth/login").permitAll() // Asegúrate de que la ruta completa esté definida
                        .requestMatchers("/alumnos/**").permitAll() // Acceso público para GET /alumnos/**
                        .requestMatchers("/profesores/**").permitAll() // Acceso público para GET /profesores/**
                        .requestMatchers("/materias/**").permitAll() // Acceso público para GET /materias/**
                        .requestMatchers("/inscripciones/**").permitAll() // Acceso público para GET /inscripciones/**

                        // Rutas protegidas solo para ADMIN
                        .requestMatchers(HttpMethod.POST, "/alumnos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/alumnos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/alumnos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/profesores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/profesores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/profesores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/materias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/materias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/materias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/inscripciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/inscripciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/inscripciones/**").hasRole("ADMIN")

                        // Requerir autenticación para cualquier otra ruta
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Agregar el filtro JWT

        return http.build();
    }
}
