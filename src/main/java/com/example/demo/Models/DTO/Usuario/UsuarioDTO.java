package com.example.demo.Models.DTO.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private Long Id;



    @NotBlank(message = "El email no puede ser nulo")
    @Email(message = "El formato debe ser el correcto: correo@ejemplo.com")
    @Size(max = 200, message = "El email solo puede tener como maximo 200 caracteres")
    private String email;



    @NotBlank(message = "La contraseña no puede ser nula")
    @Size(max = 200, message = "La contraseña solo puede tener como maximo 200 caracteres")
    private String password;


}
