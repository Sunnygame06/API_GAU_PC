package com.example.demo.Entities.Empleado;

import com.example.demo.Entities.Usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Empleado")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Empleado")
    private Long idEmpleado;

    @Column(name = "Nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "Telefono", length = 9, nullable = false)
    private String telefono;

    @Column(name = "Unidad", length = 200, nullable = false)
    private String unidad;

    @Column(name = "Rol", length = 100, nullable = false)
    private String rol;

    @Column(name = "Region", length = 100, nullable = false)
    private String region;

    @Column(name = "Departamento", length = 100, nullable = false)
    private String departamento;

    @Column(name = "Municipio", length = 100, nullable = false)
    private String municipio;

    @Column(name = "Distrito", length = 100, nullable = false)
    private String distrito;

    @Column(name = "Filtrar", length = 50)
    private String filtrar = "No Aplica";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Usuario", referencedColumnName = "Id_Usuario", nullable = false)
    private UsuarioEntity usuario; // relación 1:1 con UsuarioEntity
}
