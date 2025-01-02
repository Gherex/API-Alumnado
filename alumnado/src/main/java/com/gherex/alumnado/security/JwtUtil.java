package com.gherex.alumnado.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // No es necesario usar la contraseña aquí, ya que el JWT se genera usando un "secreto" para la firma del token
    @Value("${app.jwt.secret}")
    private String SECRET_KEY; // Aquí mantén tu clave secreta para firmar el JWT

    // Generar un token JWT
    public String generateToken(String username, String role) {
        // Generar una clave secreta de 256 bits utilizando la clase Keys de JWT
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Aseguramos que la clave sea suficientemente fuerte para HS256

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", "ROLE_" + role)  // Prefijo ROLE_
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(key, SignatureAlgorithm.HS256) // Usamos la clave generada para firmar el token
                .compact();
    }

    // Extraer el nombre de usuario del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extraer el "subject" (nombre de usuario) del token
    }

    // Extraer una claim genérica del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extraer todas las claims del token
        return claimsResolver.apply(claims); // Aplicar la función para extraer la claim específica
    }

    // Extraer todas las claims del token
    private Claims extractAllClaims(String token) {
        // Generamos la clave de 256 bits aquí también
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Aseguramos que la clave sea suficientemente fuerte para HS256

        return Jwts.parser()
                .setSigningKey(key) // Usamos la clave generada para verificar la firma del token
                .build()
                .parseClaimsJws(token) // Parsear el token
                .getBody(); // Obtener el cuerpo del token (las claims)
    }

    // Validar si el token es válido y corresponde al usuario
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token); // Extraer el nombre de usuario del token
        return (tokenUsername.equals(username) && !isTokenExpired(token)); // Verificar si el token es válido y no ha expirado
    }

    // Verificar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Verificar si la fecha de expiración del token es anterior a la fecha actual
    }

    // Extraer la fecha de expiración del token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extraer la fecha de expiración del token
    }
}
