package com.example.demo.Service.Usuario;

import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Usuario.UsuarioDTO;
import com.example.demo.Repository.Usuario.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    // ============================
    // LISTAR CON PAGINACIÓN
    // ============================
    public Page<UsuarioDTO> getAllUsuarios(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioEntity> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::convertirADTO);
    }

    // ============================
    // OBTENER POR ID
    // ============================
    public UsuarioDTO getUsuarioById(Long id) {
        UsuarioEntity entity = repo.findById(id)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario con ID " + id + " no encontrado."));
        return convertirADTO(entity);
    }

    // ============================
    // INSERTAR NUEVO USUARIO (EMAIL + PASSWORD)
    // ============================
    public UsuarioDTO insert(@Valid UsuarioDTO json) {
        if (json == null) {
            throw new IllegalArgumentException("La información del usuario no puede ser nula");
        }
        if (repo.existsByEmail(json.getEmail())) {
            throw new ExceptionDatoDuplicado("El email ya está registrado");
        }

        try {
            UsuarioEntity entity = convertirAEntity(json);
            UsuarioEntity guardado = repo.save(entity);
            return convertirADTO(guardado);
        } catch (Exception e) {
            log.error("Error al registrar el usuario: " + e.getMessage());
            throw new ExceptionDatoDuplicado("El usuario no pudo ser registrado");
        }
    }

    // ============================
    // LOGIN
    // ============================
    public UsuarioDTO login(String email, String password) {
        UsuarioEntity usuario = repo.findByEmail(email)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Email no registrado"));
        // ✨ Aquí va la verificación con hash si lo usas (Argon2, BCrypt, etc)
        if (!password.equals(usuario.getPass())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        return convertirADTO(usuario);
    }

    // ============================
    // ACTUALIZAR USUARIO
    // ============================
    public UsuarioDTO update(Long id, @Valid UsuarioDTO usuario) {
        UsuarioEntity existente = repo.findById(id)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario no encontrado"));

        existente.setEmail(usuario.getEmail());
        existente.setPass(usuario.getPassword());

        UsuarioEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }

    // ============================
    // ELIMINAR USUARIO
    // ============================
    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // ============================
    // CONVERTIDORES DTO ↔ ENTITY
    // ============================
    private UsuarioDTO convertirADTO(UsuarioEntity objEntity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(objEntity.getId());
        dto.setEmail(objEntity.getEmail());
        dto.setPassword(objEntity.getPass());
        return dto;
    }

    private UsuarioEntity convertirAEntity(@Valid UsuarioDTO json) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(json.getId());
        entity.setEmail(json.getEmail());
        entity.setPass(json.getPassword());
        return entity;
    }
}
