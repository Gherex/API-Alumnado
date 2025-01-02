package com.gherex.alumnado.controller;

import com.gherex.alumnado.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && passwordEncoder.matches(password, storedHashedPassword)) {
            return jwtUtil.generateToken(username, "ADMIN");
        }
        throw new RuntimeException("Credenciales inválidas");
    }

}
