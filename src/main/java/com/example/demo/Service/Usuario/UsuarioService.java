package com.example.demo.Service.Usuario;

import com.example.demo.Entities.Actividad.ActividadEntity;
import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Actividad.ActividadDTO;
import com.example.demo.Models.DTO.Usuario.UsuarioDTO;
import com.example.demo.Repository.Actividad.ActividadRepository;
import com.example.demo.Repository.Usuario.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@Slf4j
@CrossOrigin
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    // ============================
    // 🔹 LISTAR CON PAGINACIÓN
    // ============================
    public Page<UsuarioDTO> getAllUsuarios(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioEntity> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::convertirADTO);
    }

    // ============================
    // 🔹 INSERTAR NUEVO SOLICITANTE
    // ============================
    public UsuarioDTO insert(@Valid UsuarioDTO json) {
        if (json == null) {
            throw new IllegalArgumentException("La información del usuario no puede ser nula");
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
    // 🔹 ACTUALIZAR SOLICITANTE
    // ============================
    public UsuarioDTO update(Long id, @Valid UsuarioDTO json) {
        UsuarioEntity existente = repo.findById(id)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario no encontrado."));

        existente.setNombre(json.getNombre());
        existente.setTelefono(json.getTelefono());
        existente.setEmail(json.getEmail());
        existente.setUnidad(json.getUnidad());
        existente.setPass(json.getPass());
        existente.setRegion(json.getRegion());
        existente.setDepartamento(json.getDepartamento());
        existente.setMunicipio(json.getMunicipio());
        existente.setDistrito(json.getDistrito());
        existente.setFiltrar(json.getFiltrar());

        UsuarioEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }

    // ============================
    // 🔹 ELIMINAR SOLICITANTE
    // ============================
    public boolean delete(Long id) {
        UsuarioEntity existencia = repo.findById(id).orElse(null);
        if (existencia != null) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // ============================
    // 🔹 CONVERTIDORES DTO ↔ ENTITY
    // ============================
    private UsuarioDTO convertirADTO(UsuarioEntity objEntity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(objEntity.getId());
        dto.setNombre(objEntity.getNombre());
        dto.setTelefono(objEntity.getTelefono());
        dto.setEmail(objEntity.getEmail());
        dto.setUnidad(objEntity.getUnidad());
        dto.setPass(objEntity.getPass());
        dto.setRegion(objEntity.getRegion());
        dto.setDepartamento(objEntity.getDepartamento());
        dto.setMunicipio(objEntity.getMunicipio());
        dto.setDistrito(objEntity.getDistrito());
        dto.setFiltrar(objEntity.getFiltrar());
        return dto;
    }

    private UsuarioEntity convertirAEntity(@Valid UsuarioDTO json) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(json.getId());
        entity.setNombre(json.getNombre());
        entity.setTelefono(json.getTelefono());
        entity.setEmail(json.getEmail());
        entity.setUnidad(json.getUnidad());
        entity.setPass(json.getPass());
        entity.setRegion(json.getRegion());
        entity.setDepartamento(json.getDepartamento());
        entity.setMunicipio(json.getMunicipio());
        entity.setDistrito(json.getDistrito());
        entity.setFiltrar(json.getFiltrar());
        return entity;
    }
}
