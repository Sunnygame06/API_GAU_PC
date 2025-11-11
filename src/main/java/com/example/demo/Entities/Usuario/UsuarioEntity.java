package com.example.demo.Entities.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIO")
@Getter @Setter @ToString @EqualsAndHashCode
public class UsuarioEntity {
    private Long Id_Usuario;
    private String nombre;
    private String telefono;
    private String email;
    private String unidad;
    private String pass;
    private String rol;
    private String region;
    private String departamento;
    private String municipio;
    private String distrito;
    private String filtrar;
}
