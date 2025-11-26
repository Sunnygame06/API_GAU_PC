package com.example.demo.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    private final Key secretKey = Keys.hmacShaKeyFor(
            "MI_CLAVE_SECRETA_SUPER_LARGA_DE_256_BITS_PARA_JWT_123456789".getBytes()
    );

    private final long expirationMs = 1000 * 60 * 60; // 1 hora

    // Crear token -------------------------------------------------------
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token -----------------------------------------------------
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extraer username --------------------------------------------------
    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    // Extraer rol -------------------------------------------------------
    public String getRoleFromToken(String token) {
        return getAllClaims(token).get("role", String.class);
    }

    // Obtener claims internos -------------------------------------------
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
