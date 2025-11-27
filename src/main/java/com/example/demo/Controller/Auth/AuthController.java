package com.example.demo.Controller.Auth;

import com.example.demo.Models.DTO.Usuario.UsuarioDTO;
import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Service.Auth.AuthService;
import com.example.demo.Utils.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JWTUtils jwtUtils;

    @Value("${security.jwt.cookieName:authToken}")
    private String jwtCookieName;

    @Value("${app.env.prod:false}")
    private boolean isProd;

    @Value("${app.cookie.domain:}")
    private String cookieDomain;

    @Value("${app.auth.ttlHours:8}")
    private int ttlHours;

    // ===========================
    // LOGIN
    // ===========================
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioDTO data) {

        Optional<UsuarioEntity> userOpt = authService.authenticate(data.getEmail(), data.getPassword());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "ERROR",
                    "message", "Credenciales incorrectas"
            ));
        }

        UsuarioEntity user = userOpt.get();

        // Generar JWT
        String token = jwtUtils.generateToken(user.getEmail(), user.getEmail());
        ResponseCookie cookie = buildJwtCookie(token);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "status", "OK",
                        "token", token,
                        "user", Map.of(
                                "id", user.getId(),
                                "email", user.getEmail()
                        )
                ));
    }

    // ===========================
    // REGISTER
    // ===========================
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioDTO data) {
        try {
            UsuarioEntity nuevo = new UsuarioEntity();
            nuevo.setEmail(data.getEmail());
            nuevo.setPass(data.getPassword()); // AuthService lo encripta

            UsuarioEntity guardado = authService.register(nuevo);

            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "message", "Usuario registrado",
                    "user", Map.of(
                            "id", guardado.getId(),
                            "email", guardado.getEmail()
                    )
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "ERROR",
                    "message", e.getMessage()
            ));
        }
    }

    // ===========================
    // HELPERS COOKIE
    // ===========================
    private ResponseCookie buildJwtCookie(String token) {
        ResponseCookie.ResponseCookieBuilder b = ResponseCookie
                .from(jwtCookieName, token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(ttlHours));

        if (cookieDomain != null && !cookieDomain.isBlank()) b.domain(cookieDomain);
        if (isProd) b.sameSite("None").secure(true);
        else b.sameSite("Lax").secure(false);

        return b.build();
    }

    private ResponseCookie clearJwtCookie() {
        ResponseCookie.ResponseCookieBuilder b = ResponseCookie
                .from(jwtCookieName, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0);

        if (cookieDomain != null && !cookieDomain.isBlank()) b.domain(cookieDomain);
        if (isProd) b.sameSite("None").secure(true);
        else b.sameSite("Lax").secure(false);

        return b.build();
    }
}
