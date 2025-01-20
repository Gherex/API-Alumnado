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
    public ResponseEntity<String> login(@RequestBody com.gherex.alumnado.controller.AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        if ("admin".equals(username) && passwordEncoder.matches(password, storedHashedPassword)) {
            String token = jwtUtil.generateToken(username, "ADMIN");
            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(3600);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
    }

}