package com.example.demo.Repository.Usuario;

import com.example.demo.Entities.Usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // Buscar por email para login
    Optional<UsuarioEntity> findByEmail(String email);

    // Verificar si un email ya existe al registrar
    boolean existsByEmail(String email);
}
