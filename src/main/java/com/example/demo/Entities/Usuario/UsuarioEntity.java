package com.example.demo.Entities.Usuario;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIO")
@Getter @Setter @ToString @EqualsAndHashCode
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long Id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TELEFONO", unique = true)
    private String telefono;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "UNIDAD")
    private String unidad;

    @Column(name = "PASSWORD")
    private String pass;

    @Column(name = "ROL")
    private String rol;

    @Column(name = "REGION")
    private String region;

    @Column(name = "DEPARTAMENTO")
    private String departamento;

    @Column(name = "MUNICIPIO")
    private String municipio;

    @Column(name = "DISTRITO")
    private String distrito;

    @Column(name = "FILTRAR")
    private String filtrar;
}
