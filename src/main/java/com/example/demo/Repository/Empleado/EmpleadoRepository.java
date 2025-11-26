package com.example.demo.Repository.Empleado;

import com.example.demo.Entities.Empleado.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    // Buscar empleado por Id de Usuario
    EmpleadoEntity findByUsuarioId(Long usuarioId);
}
