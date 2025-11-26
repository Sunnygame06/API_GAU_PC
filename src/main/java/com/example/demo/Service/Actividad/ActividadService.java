package com.example.demo.Service.Actividad;

import com.example.demo.Entities.Actividad.ActividadEntity;
import com.example.demo.Entities.Empleado.EmpleadoEntity;
import com.example.demo.Entities.Usuario.UsuarioEntity;
import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Actividad.ActividadDTO;
import com.example.demo.Repository.Actividad.ActividadRepository;
import com.example.demo.Repository.Empleado.EmpleadoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ActividadService {

    @Autowired
    private ActividadRepository repo;

    @Autowired
    private EmpleadoRepository empleadoRepo;

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

        existente.setNombreActividad(json.getNombreActividad());
        existente.setFecha(json.getFecha());
        existente.setEstado(json.getEstado());
        existente.setRegion(json.getRegion());
        existente.setDepartamento(json.getDepartamento());
        existente.setMunicipio(json.getMunicipio());
        existente.setDistrito(json.getDistrito());
        existente.setHoraInicio(json.getHoraInicio());
        existente.setHoraFin(json.getHoraFin());
        existente.setHombres(json.getHombres());
        existente.setMujeres(json.getMujeres());
        existente.setObservaciones(json.getObservaciones());
        existente.setResultados(json.getResultados());
        existente.setTarea(json.getTareas() == null ? "" : String.join(",", json.getTareas()));
        existente.setRespaldo(json.getRespaldo());

        // 🔥 relacionar empleado
        if (json.getIdEmpleado() != null) {
            EmpleadoEntity empleado = empleadoRepo.findById(json.getIdEmpleado())
                    .orElseThrow(() -> new ExceptionDatoNoEncontrado("Empleado no encontrado."));
            existente.setEmpleado(empleado);
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
        dto.setNombreActividad(obj.getNombreActividad());
        dto.setFecha(obj.getFecha());
        dto.setEstado(obj.getEstado());
        dto.setRegion(obj.getRegion());
        dto.setDepartamento(obj.getDepartamento());
        dto.setMunicipio(obj.getMunicipio());
        dto.setDistrito(obj.getDistrito());
        dto.setHoraInicio(obj.getHoraInicio());
        dto.setHoraFin(obj.getHoraFin());
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

        // 🔥 empleado
        if (obj.getEmpleado() != null) {
            dto.setIdEmpleado(obj.getEmpleado().getIdEmpleado());
        }

        return dto;
    }

    // ============================
    // CONVERTIR DTO → ENTITY
    // ============================
    private ActividadEntity convertirAEntity(@Valid ActividadDTO json) {

        ActividadEntity e = new ActividadEntity();

        e.setNombreActividad(json.getNombreActividad());
        e.setFecha(json.getFecha());
        e.setEstado(json.getEstado());
        e.setRegion(json.getRegion());
        e.setDepartamento(json.getDepartamento());
        e.setMunicipio(json.getMunicipio());
        e.setDistrito(json.getDistrito());
        e.setHoraInicio(json.getHoraInicio());
        e.setHoraFin(json.getHoraFin());
        e.setHombres(json.getHombres());
        e.setMujeres(json.getMujeres());
        e.setObservaciones(json.getObservaciones());
        e.setResultados(json.getResultados());
        e.setRespaldo(json.getRespaldo());
        e.setTarea(json.getTareas() == null ? "" : String.join(",", json.getTareas()));

        // 🔥 asignar empleado
        if (json.getIdEmpleado() != null) {
            EmpleadoEntity empleado = empleadoRepo.findById(json.getIdEmpleado())
                    .orElseThrow(() -> new ExceptionDatoNoEncontrado("Empleado no encontrado."));
            e.setEmpleado(empleado);
        }

        return e;
    }
}
