package com.example.demo.Service.Actividad;

import com.example.demo.Entities.Actividad.ActividadEntity;
import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Actividad.ActividadDTO;
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

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@CrossOrigin
public class ActividadService {

    @Autowired
    private ActividadRepository repo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    // ============================
    // LISTAR
    // ============================
    public Page<ActividadDTO> getAllActividades(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ActividadEntity> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::convertirADTO);
    }

    // ============================
    // INSERTAR
    // ============================
    public ActividadDTO insert(@Valid ActividadDTO json) {
        if (json == null) {
            throw new IllegalArgumentException("Datos nulos");
        }

        try {
            ActividadEntity entity = convertirAEntity(json);

            ActividadEntity guardado = repo.save(entity);
            return convertirADTO(guardado);

        } catch (Exception e) {
            log.error("Error al registrar actividad: {}", e.getMessage());
            throw new ExceptionDatoDuplicado("No se pudo registrar la actividad.");
        }
    }

    // ============================
    // ACTUALIZAR
    // ============================
    public ActividadDTO update(Long id, @Valid ActividadDTO json) {

        ActividadEntity existente = repo.findById(id)
                .orElseThrow(() -> new ExceptionDatoNoEncontrado("Actividad no encontrada."));

        existente.setActividad_nombre(json.getActividad_nombre());
        existente.setFecha(json.getFecha());
        existente.setEstado(json.getEstado());
        existente.setRegion(json.getRegion());
        existente.setDepartamento(json.getDepartamento());
        existente.setMunicipio(json.getMunicipio());
        existente.setDistrito(json.getDistrito());
        existente.setH_inicio(json.getH_inicio());
        existente.setH_Fin(json.getH_Fin());
        existente.setHombres(json.getHombres());
        existente.setMujeres(json.getMujeres());
        existente.setObservaciones(json.getObservaciones());
        existente.setResultados(json.getResultados());
        existente.setTarea(json.getTareas() == null ? "" : String.join(",", json.getTareas()));
        existente.setRespaldo(json.getRespaldo());

        // 🔥 NUEVO: relacionar usuario
        if (json.getId_Usuario() != null) {
            UsuarioEntity usuario = usuarioRepo.findById(json.getId_Usuario())
                    .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario no encontrado."));
            existente.setUsuario(usuario);
        }

        ActividadEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }

    // ============================
    // ELIMINAR
    // ============================
    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // ============================
    // CONVERTIR ENTITY → DTO
    // ============================
    private ActividadDTO convertirADTO(ActividadEntity obj) {
        ActividadDTO dto = new ActividadDTO();

        dto.setId(obj.getId());
        dto.setActividad_nombre(obj.getActividad_nombre());
        dto.setFecha(obj.getFecha());
        dto.setEstado(obj.getEstado());
        dto.setRegion(obj.getRegion());
        dto.setDepartamento(obj.getDepartamento());
        dto.setMunicipio(obj.getMunicipio());
        dto.setDistrito(obj.getDistrito());
        dto.setH_inicio(obj.getH_inicio());
        dto.setH_Fin(obj.getH_Fin());
        dto.setHombres(obj.getHombres());
        dto.setMujeres(obj.getMujeres());
        dto.setObservaciones(obj.getObservaciones());
        dto.setResultados(obj.getResultados());
        dto.setTareas(
                obj.getTarea() == null || obj.getTarea().isEmpty()
                        ? List.of()
                        : Arrays.asList(obj.getTarea().split(","))
        );
        dto.setRespaldo(obj.getRespaldo());

        // 🔥 usuario
        if (obj.getUsuario() != null) {
            dto.setId_Usuario(obj.getUsuario().getId());
        }

        return dto;
    }

    // ============================
    // CONVERTIR DTO → ENTITY
    // ============================
    private ActividadEntity convertirAEntity(@Valid ActividadDTO json) {

        ActividadEntity e = new ActividadEntity();

        e.setActividad_nombre(json.getActividad_nombre());
        e.setFecha(json.getFecha());
        e.setEstado(json.getEstado());
        e.setRegion(json.getRegion());
        e.setDepartamento(json.getDepartamento());
        e.setMunicipio(json.getMunicipio());
        e.setDistrito(json.getDistrito());
        e.setH_inicio(json.getH_inicio());
        e.setH_Fin(json.getH_Fin());
        e.setHombres(json.getHombres());
        e.setMujeres(json.getMujeres());
        e.setObservaciones(json.getObservaciones());
        e.setResultados(json.getResultados());
        e.setRespaldo(json.getRespaldo());
        e.setTarea(json.getTareas() == null ? "" : String.join(",", json.getTareas()));

        // 🔥 asignar usuario
        if (json.getId_Usuario() != null) {
            UsuarioEntity usuario = usuarioRepo.findById(json.getId_Usuario())
                    .orElseThrow(() -> new ExceptionDatoNoEncontrado("Usuario no encontrado."));
            e.setUsuario(usuario);
        }

        return e;
    }
}
