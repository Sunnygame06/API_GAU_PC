package com.example.demo.Controller.Empleado;

import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Empleado.EmpleadoDTO;
import com.example.demo.Service.Empleado.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/apiEmpleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    // 🔹 GET ALL EMPLEADOS
    @GetMapping("/getAllEmpleados")
    public ResponseEntity<?> getAllEmpleados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (size <= 0 || size > 50) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la página debe estar entre 1 y 50"
            ));
        }

        Page<EmpleadoDTO> empleados = service.getAllEmpleados(page, size);

        if (empleados == null || empleados.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "content", new Object[]{},
                    "empty", true
            ));
        }

        return ResponseEntity.ok(empleados);
    }

    // 🔹 GET EMPLEADO POR ID
    @GetMapping("/getEmpleado/{id}")
    public ResponseEntity<?> getEmpleadoById(@PathVariable Long id) {
        try {
            EmpleadoDTO empleado = service.getEmpleadoById(id);
            return ResponseEntity.ok(empleado);
        } catch (ExceptionDatoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Empleado no encontrado"));
        }
    }

    // 🔹 CREAR EMPLEADO
    @PostMapping("/newEmpleado")
    public ResponseEntity<?> createEmpleado(@Valid @RequestBody EmpleadoDTO json) {
        try {
            EmpleadoDTO response = service.insert(json);
            if (response == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Inserción incorrecta"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Completado",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // 🔹 ACTUALIZAR EMPLEADO
    @PutMapping("/updateEmpleado/{id}")
    public ResponseEntity<?> updateEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoDTO empleado) {
        try {
            EmpleadoDTO empleadoActualizado = service.update(id, empleado);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (ExceptionDatoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Empleado no encontrado"));
        } catch (ExceptionDatoDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado()
            ));
        }
    }

    // 🔹 ELIMINAR EMPLEADO
    @DeleteMapping("/deleteEmpleado/{id}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
        try {
            if (!service.delete(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "error", "Empleado no encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", "Completado",
                    "message", "Empleado eliminado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", e.getMessage()
            ));
        }
    }
}
