package com.example.demo.Service.Auth;

import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Repository.Usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ============================================================
    // AUTENTICAR
    // ============================================================

    public Optional<UsuarioEntity> authenticate(String email, String pass) {
        Optional<UsuarioEntity> userOpt = usuarioRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        UsuarioEntity user = userOpt.get();

        // Validar contraseña
        if (!passwordEncoder.matches(pass, user.getPass())) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    // ============================================================
    // REGISTRO
    // ============================================================

    public UsuarioEntity register(UsuarioEntity nuevo) {

        // Validar si el correo ya existe
        if (usuarioRepository.existsByEmail(nuevo.getEmail())) {
            throw new RuntimeException("El correo ya está en uso");
        }

        // Encriptar contraseña
        String encrypted = passwordEncoder.encode(nuevo.getPass());
        nuevo.setPass(encrypted);

        return usuarioRepository.save(nuevo);
    }
}
