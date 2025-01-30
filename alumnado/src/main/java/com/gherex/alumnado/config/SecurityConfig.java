package com.gherex.alumnado.config;

import com.gherex.alumnado.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Asegúrate de que las rutas coincidan con tu contexto
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").permitAll() // GET públicos
                        .requestMatchers(HttpMethod.POST, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").hasRole("ADMIN") // Solo ADMIN
                        .requestMatchers(HttpMethod.PUT, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").hasRole("ADMIN") // Solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/alumnos/**", "/profesores/**", "/materias/**", "/inscripciones/**").hasRole("ADMIN") // Solo ADMIN
                        // Opcion 2:
                        // .requestMatchers("/alumnado/api/v1/auth/login").permitAll()
                        // .requestMatchers(HttpMethod.GET, "/alumnado/api/v1/alumnos/**").permitAll()
                        // .requestMatchers(HttpMethod.POST, "/alumnado/api/v1/alumnos/**").hasRole("ADMIN")
                        // Repite para otras rutas (profesores, materias, etc.)
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:5176",
                "https://alumnado-de-gherex.netlify.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Exponer el encabezado para respuestas
        configuration.setAllowCredentials(true);  // Importante para cookies/tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}