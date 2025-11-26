package com.example.demo.Service.Empleado;

import com.example.demo.Entities.Empleado.EmpleadoEntity;
import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Empleado.EmpleadoDTO;
import com.example.demo.Repository.Empleado.EmpleadoRepository;
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
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    // ============================
    // 🔹 LISTAR EMPLEADOS CON PAGINACIÓN
    // ============================
    public Page<EmpleadoDTO> getAllEmpleados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmpleadoEntity> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::convertirADTO);
    }

    // ============================
    // 🔹 OBTENER EMPLEADO POR ID
    // ============================
    public EmpleadoDTO getEmpleadoById(Long id) {
        EmpleadoEntity entity = repo.findById(id)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Empleado con ID " + id + " no encontrado."));
        return convertirADTO(entity);
    }

    // ============================
    // 🔹 INSERTAR NUEVO EMPLEADO
    // ============================
    public EmpleadoDTO insert(@Valid EmpleadoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos del empleado no pueden ser nulos");
        }

        try {
            EmpleadoEntity entity = convertirAEntity(dto);

            // Relacionar con Usuario
            if (dto.getIdEmpleado() != null) {
                UsuarioEntity usuario = usuarioRepo.findById(dto.getIdEmpleado())
                        .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario no encontrado."));
                entity.setUsuario(usuario);
            } else {
                throw new IllegalArgumentException("Se requiere un Usuario válido para el empleado");
            }

            EmpleadoEntity guardado = repo.save(entity);
            return convertirADTO(guardado);
        } catch (Exception e) {
            log.error("Error al registrar empleado: {}", e.getMessage());
            throw new ExceptionDatoDuplicado("No se pudo registrar el empleado.");
        }
    }

    // ============================
    // 🔹 ACTUALIZAR EMPLEADO
    // ============================
    public EmpleadoDTO update(Long id, @Valid EmpleadoDTO dto) {
        EmpleadoEntity existente = repo.findById(id)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Empleado no encontrado."));

        existente.setNombre(dto.getNombre());
        existente.setTelefono(dto.getTelefono());
        existente.setUnidad(dto.getUnidad());
        existente.setRol(dto.getRol());
        existente.setRegion(dto.getRegion());
        existente.setDepartamento(dto.getDepartamento());
        existente.setMunicipio(dto.getMunicipio());
        existente.setDistrito(dto.getDistrito());
        existente.setFiltrar(dto.getFiltrar());

        // Actualizar Usuario relacionado
        if (dto.getIdEmpleado() != null) {
            UsuarioEntity usuario = usuarioRepo.findById(dto.getIdEmpleado())
                    .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario no encontrado."));
            existente.setUsuario(usuario);
        }

        EmpleadoEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }

    // ============================
    // 🔹 ELIMINAR EMPLEADO
    // ============================
    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // ============================
    // 🔹 CONVERTIDORES ENTITY ↔ DTO
    // ============================
    private EmpleadoDTO convertirADTO(EmpleadoEntity entity) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setIdEmpleado(entity.getIdEmpleado());
        dto.setNombre(entity.getNombre());
        dto.setTelefono(entity.getTelefono());
        dto.setUnidad(entity.getUnidad());
        dto.setRol(entity.getRol());
        dto.setRegion(entity.getRegion());
        dto.setDepartamento(entity.getDepartamento());
        dto.setMunicipio(entity.getMunicipio());
        dto.setDistrito(entity.getDistrito());
        dto.setFiltrar(entity.getFiltrar());

        if (entity.getUsuario() != null) {
            dto.setIdEmpleado(entity.getUsuario().getId());
        }
        return dto;
    }

    private EmpleadoEntity convertirAEntity(@Valid EmpleadoDTO dto) {
        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setIdEmpleado(dto.getIdEmpleado());
        entity.setNombre(dto.getNombre());
        entity.setTelefono(dto.getTelefono());
        entity.setUnidad(dto.getUnidad());
        entity.setRol(dto.getRol());
        entity.setRegion(dto.getRegion());
        entity.setDepartamento(dto.getDepartamento());
        entity.setMunicipio(dto.getMunicipio());
        entity.setDistrito(dto.getDistrito());
        entity.setFiltrar(dto.getFiltrar());
        return entity;
    }
}
