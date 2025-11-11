package com.example.demo.Entities.Actividad;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ACTIVIDAD")
@Getter @Setter @ToString @EqualsAndHashCode
public class ActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACTIVIDAD")
    private Long id;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "HORA_INICIO")
    private LocalTime H_inicio;

    @Column(name = "HORA_FIN")
    private LocalTime H_Fin;

    @Column(name = "REGION")
    private String region;

    @Column(name = "DEPARTAMENTO")
    private String departamento;

    @Column(name = "MUNICIPIO")
    private String municipio;

    @Column(name = "DISTRITO")
    private String distrito;

    @Column(name = "ACTIVIDAD_NOMBRE")
    private String actividad_nombre;

    @Column(name = "TAREA")
    private String tarea;

    @Column(name = "HOMBRES")
    private Long hombres;

    @Column(name = "MUJERES")
    private Long mujeres;

    @Column(name = "RESULTADOS")
    private String resultados;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "RESPALDO")
    private String respaldo;

    @Column(name = "ID_USUARIO")
    private Long Id_Usuario;
}
