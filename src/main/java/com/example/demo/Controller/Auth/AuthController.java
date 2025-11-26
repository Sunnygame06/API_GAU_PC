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

    // ============================================================
    // LOGIN
    // ============================================================

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioDTO data) {

        if (data.getEmail() == null || data.getEmail().isBlank()
                || data.getPass() == null || data.getPass().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "ERROR",
                    "message", "Credenciales incompletas"
            ));
        }

        Optional<UsuarioEntity> userOpt = authService.authenticate(data.getEmail(), data.getPass());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "ERROR",
                    "message", "Credenciales incorrectas"
            ));
        }

        UsuarioEntity user = userOpt.get();

        // Crear JWT
        String subject = user.getEmail();
        String role = user.getRol();
        String token = jwtUtils.generateToken(subject, role);

        ResponseCookie cookie = buildJwtCookie(token);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "status", "OK",
                        "token", token,
                        "user", Map.of(
                                "id", user.getId(),
                                "nombre", user.getNombre(),
                                "email", user.getEmail(),
                                "rol", user.getRol(),
                                "unidad", user.getUnidad()
                        )
                ));
    }

    // ============================================================
    // LOGOUT
    // ============================================================

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie clear = clearJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clear.toString())
                .body(Map.of(
                        "status", "OK",
                        "message", "Sesión cerrada"
                ));
    }

    // ============================================================
    // REGISTRO
    // ============================================================

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioDTO data) {
        try {
            UsuarioEntity nuevo = new UsuarioEntity();

            nuevo.setNombre(data.getNombre());
            nuevo.setTelefono(data.getTelefono());
            nuevo.setEmail(data.getEmail());
            nuevo.setUnidad(data.getUnidad());
            nuevo.setRol(data.getRol());
            nuevo.setRegion(data.getRegion());
            nuevo.setDepartamento(data.getDepartamento());
            nuevo.setMunicipio(data.getMunicipio());
            nuevo.setDistrito(data.getDistrito());
            nuevo.setPass(data.getPass()); // se encripta en AuthService

            UsuarioEntity guardado = authService.register(nuevo);

            return ResponseEntity.ok(Map.of(
                    "status", "OK",
                    "message", "Usuario registrado",
                    "user", Map.of(
                            "id", guardado.getId(),
                            "nombre", guardado.getNombre(),
                            "email", guardado.getEmail(),
                            "rol", guardado.getRol()
                    )
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "ERROR",
                    "message", e.getMessage()
            ));
        }
    }

    // ============================================================
    // ME (Usuario autenticado)
    // ============================================================

    @GetMapping("/me")
    public ResponseEntity<?> me(org.springframework.security.core.Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "ERROR",
                    "message", "No autenticado"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "user", Map.of(
                        "username", auth.getName(),
                        "roles", auth.getAuthorities().stream().map(a -> a.getAuthority()).toList()
                )
        ));
    }

    // ============================================================
    // HELPERS COOKIE
    // ============================================================

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
