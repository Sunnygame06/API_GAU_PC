package com.example.demo.Models.DTO.Empleado;

import com.example.demo.Models.DTO.Usuario.UsuarioDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoDTO {

    private Long idEmpleado;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(max = 200, message = "El nombre solo puede tener como máximo 200 caracteres")
    private String nombre;

    @NotBlank(message = "El teléfono no puede ser nulo")
    @Size(max = 9, message = "El teléfono solo puede tener como máximo 9 caracteres")
    private String telefono;

    @NotBlank(message = "La unidad no puede ser nula")
    @Size(max = 200, message = "La unidad solo puede tener como máximo 200 caracteres")
    private String unidad;

    @NotBlank(message = "El rol no puede ser nulo")
    @Size(max = 100, message = "El rol solo puede tener como máximo 100 caracteres")
    private String rol;

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

    @Size(max = 50, message = "El filtro solo puede tener como máximo 50 caracteres")
    private String filtrar;

    @NotNull(message = "El usuario asociado no puede ser nulo")
    private UsuarioDTO usuario; // Relación 1:1 con Usuario
}
