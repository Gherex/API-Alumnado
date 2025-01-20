package com.gherex.alumnado.controller;

import com.gherex.alumnado.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.password.hash}")
    private String storedHashedPassword;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if ("admin".equals(username) && passwordEncoder.matches(password, storedHashedPassword)) {
            String token = jwtUtil.generateToken(username, "ADMIN");

            // Crear la cookie
            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true); // Para que la cookie no sea accesible por JavaScript
            cookie.setSecure(true); // Asegúrate de que esta cookie solo se envíe por HTTPS
            cookie.setPath("/"); // El path donde la cookie estará disponible
            cookie.setMaxAge(3600); // Expira en 1 hora

            // Retornar la respuesta con la cookie configurada
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
    }
}

class LoginRequest {
    private String username;
    private String password;

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
