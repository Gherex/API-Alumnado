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
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF porque estamos usando una API REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir todos los orígenes localhost dinámicamente
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:*", // Esto incluye cualquier puerto para localhost
                "https://alumnado-de-gherex.netlify.app"
        ));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));

        // Permitir el envío de cookies o credenciales
        configuration.setAllowCredentials(true);

        // Aplicar la configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
