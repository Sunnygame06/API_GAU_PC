package com.example.demo.Models.DTO.Actividad;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
public class ActividadDTO {

    private Long id;

    @NotBlank(message = "El estado no puede ser nulo")
    @Size(max = 100, message = "El estado solo puede tener como maximo 100 caracteres")
    private String estado;

    private LocalDate fecha;

    private LocalTime H_inicio;

    private LocalTime H_Fin;

    @NotBlank(message = "La region no puede ser nulo")
    @Size(max = 100, message = "La region solo puede tener como maximo 100 caracteres")
    private String region;

    @NotBlank(message = "El departamento no puede ser nulo")
    @Size(max = 100, message = "El departamento solo puede tener como maximo 100 caracteres")
    private String departamento;

    @NotBlank(message = "El municipio no puede ser nulo")
    @Size(max = 100, message = "El municipio solo puede tener como maximo 100 caracteres")
    private String municipio;

    @NotBlank(message = "El distrito no puede ser nulo")
    @Size(max = 100, message = "El distrito solo puede tener como maximo 100 caracteres")
    private String distrito;

    @NotBlank(message = "La Actividad Nombre no puede ser nula")
    @Size(max = 100, message = "La Actividad Nombre solo puede tener como maximo 100 caracteres")
    private String actividad_nombre;

    @NotBlank(message = "La tarea no puede ser nula")
    @Size(max = 100, message = "La tarea solo puede tener como maximo 100 caracteres")
    private String tarea;

    @Min(value = 0, message = "La cantidad debe ser mayor que 0")
    private Long hombres;

    @Min(value = 0, message = "La cantidad debe ser mayor que 0")
    private Long mujeres;

    @NotBlank(message = "El resultado no puede ser nulo")
    @Size(max = 100, message = "El resultado solo puede tener como maximo 1000 caracteres")
    private String resultados;

    @NotBlank(message = "La observacion no puede ser nula")
    @Size(max = 100, message = "La observacion solo puede tener como maximo 1000 caracteres")
    private String observaciones;

    @Size(max = 100, message = "El respaldo solo puede tener como maximo 500 caracteres")
    private String respaldo;

    private Long Id_Usuario;
}
