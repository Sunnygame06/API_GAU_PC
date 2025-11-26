package com.example.demo.Models.DTO.Actividad;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ActividadDTO {

    private Long id;

    @NotBlank(message = "El estado no puede ser nulo")
    @Size(max = 100, message = "El estado solo puede tener como máximo 100 caracteres")
    private String estado;

    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate fecha;

    @NotBlank(message = "La hora de inicio no puede ser nula")
    @Size(max = 10, message = "La hora de inicio solo puede tener como máximo 10 caracteres")
    private String horaInicio;

    @NotBlank(message = "La hora de fin no puede ser nula")
    @Size(max = 10, message = "La hora de fin solo puede tener como máximo 10 caracteres")
    private String horaFin;

    @NotBlank(message = "La región no puede ser nula")
    @Size(max = 100, message = "La región solo puede tener como máximo 100 caracteres")
    private String region;

    @NotBlank(message = "El departamento no puede ser nulo")
    @Size(max = 100, message = "El departamento solo puede tener como máximo 100 caracteres")
    private String departamento;

    @NotBlank(message = "El municipio no puede ser nulo")
    @Size(max = 100, message = "El municipio solo puede tener como máximo 100 caracteres")
    private String municipio;

    @NotBlank(message = "El distrito no puede ser nulo")
    @Size(max = 100, message = "El distrito solo puede tener como máximo 100 caracteres")
    private String distrito;

    @NotBlank(message = "El nombre de la actividad no puede ser nulo")
    @Size(max = 500, message = "El nombre de la actividad solo puede tener como máximo 500 caracteres")
    private String nombreActividad;

    private List<String> tareas;

    @Min(value = 0, message = "La cantidad de hombres debe ser mayor o igual a 0")
    private Long hombres = 0L;

    @Min(value = 0, message = "La cantidad de mujeres debe ser mayor o igual a 0")
    private Long mujeres = 0L;

    @NotBlank(message = "Los resultados no pueden ser nulos")
    @Size(max = 1000, message = "Los resultados solo pueden tener como máximo 1000 caracteres")
    private String resultados;

    @NotBlank(message = "Las observaciones no pueden ser nulas")
    @Size(max = 1000, message = "Las observaciones solo pueden tener como máximo 1000 caracteres")
    private String observaciones;

    @Size(max = 500, message = "El respaldo solo puede tener como máximo 500 caracteres")
    private String respaldo;

    private Long idEmpleado; // Relación con empleado
}
