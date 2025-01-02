package com.gherex.alumnado.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    @Value("${app.password.hash}") // Cargar el hash desde el archivo properties
    private String passwordHash;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method to verify si la contraseña proporcionada coincide con el hash almacenado
    public boolean validatePassword(String password) {
        return passwordEncoder.matches(password, passwordHash);
    }
}
