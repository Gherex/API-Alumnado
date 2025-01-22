package com.gherex.alumnado.controller;

import com.gherex.alumnado.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.password.hash}")
    private String storedHashedPassword;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody com.gherex.alumnado.controller.AuthRequest authRequest, HttpServletResponse response) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        if ("admin".equals(username) && passwordEncoder.matches(password, storedHashedPassword)) {
            String token = jwtUtil.generateToken(username, "ADMIN");

            // Configurar la cookie
            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(cookieSecure); // Cambia dinámicamente dependiendo del entorno
            cookie.setPath("/");
            cookie.setMaxAge(3600); // 1 hora

            // Agregar la cookie a la respuesta
            response.addCookie(cookie);

            // Retornar respuesta
            return ResponseEntity.ok("Autenticación exitosa");
        }

        // Lanza una excepción si las credenciales son inválidas
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
    }
}
