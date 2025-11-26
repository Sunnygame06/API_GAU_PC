package com.example.demo.Entities.Actividad;

import com.example.demo.Entities.Empleado.EmpleadoEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "ACTIVIDAD")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACTIVIDAD")
    private Long id;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @Column(name = "HORA_INICIO", length = 10)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 10)
    private String horaFin;

    @Column(name = "REGION", length = 100)
    private String region;

    @Column(name = "DEPARTAMENTO", length = 100)
    private String departamento;

    @Column(name = "MUNICIPIO", length = 100)
    private String municipio;

    @Column(name = "DISTRITO", length = 100)
    private String distrito;

    @Column(name = "ACTIVIDAD_NOMBRE", length = 500)
    private String nombreActividad;

    @Column(name = "TAREA", length = 300)
    private String tarea;

    @Column(name = "HOMBRES")
    private Long hombres = 0L;

    @Column(name = "MUJERES")
    private Long mujeres = 0L;

    @Column(name = "RESULTADOS", length = 1000)
    private String resultados;

    @Column(name = "OBSERVACIONES", length = 1000)
    private String observaciones;

    @Column(name = "RESPALDO", length = 500)
    private String respaldo;

    // 🔥 RELACIÓN CON EMPLEADO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLEADO", nullable = false)
    private EmpleadoEntity empleado;
}
