package com.example.demo.Repository.Actividad;

import com.example.demo.Entities.Actividad.ActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadEntity, Long> {
}
