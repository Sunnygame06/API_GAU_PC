package com.example.demo.Service.Auth;

import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Repository.Usuario.UsuarioRepository;
import com.example.demo.Config.Argon2.Argon2Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final Argon2Password argon2Password;

    // ===========================
    // AUTENTICAR USUARIO
    // ===========================
    public Optional<UsuarioEntity> authenticate(String email, String password) {
        Optional<UsuarioEntity> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isEmpty()) return Optional.empty();

        UsuarioEntity user = userOpt.get();

        // Verificar contraseña con Argon2
        if (!argon2Password.VerifyPassword(user.getPass(), password)) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    // ===========================
    // REGISTRAR USUARIO
    // ===========================
    public UsuarioEntity register(UsuarioEntity nuevo) {
        if (usuarioRepository.existsByEmail(nuevo.getEmail())) {
            throw new RuntimeException("El correo ya está en uso");
        }

        // Encriptar contraseña
        String encrypted = argon2Password.EncryptPassword(nuevo.getPass());
        nuevo.setPass(encrypted);

        return usuarioRepository.save(nuevo);
    }
}
