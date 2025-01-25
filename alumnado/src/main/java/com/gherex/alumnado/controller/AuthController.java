package com.gherex.alumnado.controller;

import com.gherex.alumnado.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // El hash de la contraseña se obtiene desde application.properties
    @Value("${app.password.hash}")
    private String storedPasswordHash;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        // Validar usuario y contraseña
        if ("admin".equals(username) && passwordEncoder.matches(password, storedPasswordHash)) {
            // Generar el token
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}
