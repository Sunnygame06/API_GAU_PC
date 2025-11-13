package com.example.demo.Models.DTO.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private Long Id;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(max = 200, message = "El nombre solo puede tener como maximo 200 caracteres")
    private String nombre;

    @NotBlank(message = "El telefono no puede ser nulo")
    @Size(max = 9, message = "El telefono solo puede tener como maximo 9 caracteres")
    private String telefono;

    @NotBlank(message = "El email no puede ser nulo")
    @Email(message = "El formato debe ser el correcto: correo@ejemplo.com")
    @Size(max = 200, message = "El email solo puede tener como maximo 200 caracteres")
    private String email;

    @NotBlank(message = "La unidad no puede ser nulo")
    @Size(max = 200, message = "La unidad solo puede tener como maximo 200 caracteres")
    private String unidad;

    @NotBlank(message = "La contraseña no puede ser nula")
    @Size(max = 200, message = "La contraseña solo puede tener como maximo 200 caracteres")
    private String pass;

    @NotBlank(message = "El rol no puede ser nulo")
    @Size(max = 100, message = "El rol solo puede tener como maximo 100 caracteres")
    private String rol;

    @NotBlank(message = "La region no puede ser nula")
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

    @Size(max = 50, message = "El filtro solo puede tener como maximo 50 caracteres")
    private String filtrar;
}
