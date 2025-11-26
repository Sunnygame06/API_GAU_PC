package com.example.demo.Entities.Usuario;

import com.example.demo.Entities.Actividad.ActividadEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "USUARIO")
@Getter @Setter @ToString @EqualsAndHashCode
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long Id;

    @Column(name = "EMAIL", unique = true)
    private String email;


    @Column(name = "PASSWORD")
    private String pass;


}
