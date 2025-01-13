package com.gherex.alumnado.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/alumnos/**").permitAll()
                        .requestMatchers("/profesores/**").permitAll()
                        .requestMatchers("/materias/**").permitAll()
                        .requestMatchers("/inscripciones/**").permitAll()
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
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://tu-dominio-en-produccion.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Permitir el envío de cookies o credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
