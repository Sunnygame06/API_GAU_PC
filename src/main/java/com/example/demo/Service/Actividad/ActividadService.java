package com.example.demo.Service.Actividad;

import com.example.demo.Entities.Actividad.ActividadEntity;
import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Actividad.ActividadDTO;
import com.example.demo.Repository.Actividad.ActividadRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.stream.Collectors;

@Service
@Slf4j
@CrossOrigin
public class ActividadService {

    @Autowired
    private ActividadRepository repo;

    // ============================
    // 🔹 LISTAR CON PAGINACIÓN
    // ============================
    public Page<ActividadDTO> getAllActividades(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ActividadEntity> pageEntity = repo.findAll(pageable);
        return pageEntity.map(this::convertirADTO);
    }

    // ============================
    // 🔹 INSERTAR NUEVO SOLICITANTE
    // ============================
    public ActividadDTO insert(@Valid ActividadDTO json) {
        if (json == null) {
            throw new IllegalArgumentException("La información de la actividad no puede ser nula");
        }
        try {
            ActividadEntity entity = convertirAEntity(json);
            ActividadEntity guardado = repo.save(entity);
            return convertirADTO(guardado);
        } catch (Exception e) {
            log.error("Error al registrar la actividad: " + e.getMessage());
            throw new ExceptionDatoDuplicado("La actividad no pudo ser registrada");
        }
    }

    // ============================
    // 🔹 ACTUALIZAR SOLICITANTE
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
        existente.setH_Fin(json.getH_Fin());
        existente.setH_inicio(json.getH_inicio());
        existente.setHombres(json.getHombres());
        existente.setMujeres(json.getMujeres());
        existente.setObservaciones(json.getObservaciones());
        existente.setResultados(json.getResultados());
        existente.setTarea(json.getTarea());
        existente.setRespaldo(json.getRespaldo());
        existente.setId_Usuario(json.getId_Usuario());

        ActividadEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }

    // ============================
    // 🔹 ELIMINAR SOLICITANTE
    // ============================
    public boolean delete(Long id) {
        ActividadEntity existencia = repo.findById(id).orElse(null);
        if (existencia != null) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // ============================
    // 🔹 CONVERTIDORES DTO ↔ ENTITY
    // ============================
    private ActividadDTO convertirADTO(ActividadEntity objEntity) {
        ActividadDTO dto = new ActividadDTO();
        dto.setId(objEntity.getId());
        dto.setActividad_nombre(objEntity.getActividad_nombre());
        dto.setFecha(objEntity.getFecha());
        dto.setEstado(objEntity.getEstado());
        dto.setRegion(objEntity.getRegion());
        dto.setDepartamento(objEntity.getDepartamento());
        dto.setMunicipio(objEntity.getMunicipio());
        dto.setH_Fin(objEntity.getH_Fin());
        dto.setH_inicio(objEntity.getH_inicio());
        dto.setHombres(objEntity.getHombres());
        dto.setMujeres(objEntity.getMujeres());
        dto.setObservaciones(objEntity.getObservaciones());
        dto.setResultados(objEntity.getResultados());
        dto.setTarea(objEntity.getTarea());
        dto.setRespaldo(objEntity.getRespaldo());
        dto.setId_Usuario(objEntity.getId_Usuario());
        return dto;
    }

    private ActividadEntity convertirAEntity(@Valid ActividadDTO json) {
        ActividadEntity entity = new ActividadEntity();
        entity.setId(json.getId());
        entity.setActividad_nombre(json.getActividad_nombre());
        entity.setFecha(json.getFecha());
        entity.setEstado(json.getEstado());
        entity.setRegion(json.getRegion());
        entity.setDepartamento(json.getDepartamento());
        entity.setMunicipio(json.getMunicipio());
        entity.setH_Fin(json.getH_Fin());
        entity.setH_inicio(json.getH_inicio());
        entity.setHombres(json.getHombres());
        entity.setMujeres(json.getMujeres());
        entity.setObservaciones(json.getObservaciones());
        entity.setResultados(json.getResultados());
        entity.setTarea(json.getTarea());
        entity.setRespaldo(json.getRespaldo());
        entity.setId_Usuario(json.getId_Usuario());
        return entity;
    }
}
