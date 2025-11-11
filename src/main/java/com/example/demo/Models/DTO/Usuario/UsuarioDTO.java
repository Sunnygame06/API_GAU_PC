package com.example.demo.Models.DTO.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private Long Id_Usuario;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(max = 200, message = "El nombre solo puede tener como maximo 200 caracteres")
    private String nombre;

    @NotBlank(message = "El telefono no puede ser nulo")
    @Size(max = 9, message = "El telefono solo puede tener como maximo 9 caracteres")
    private String telefono;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Email(message = "El formato debe ser el correcto: correo@ejemplo.com")
    @Size(max = 200, message = "El nombre solo puede tener como maximo 200 caracteres")
    private String email;

    @NotBlank(message = "La unidad no puede ser nulo")
    @Size(max = 200, message = "La unidad solo puede tener como maximo 200 caracteres")
    private String unidad;

    @NotBlank(message = "La contraseña no puede ser nula")
    @Size(max = 200, message = "La contraseña solo puede tener como maximo 200 caracteres")
    private String pass;
    private String rol;
    private String region;
    private String departamento;
    private String municipio;
    private String distrito;
    private String filtrar;
}
