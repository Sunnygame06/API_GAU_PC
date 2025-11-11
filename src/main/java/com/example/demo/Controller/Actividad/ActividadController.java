package com.example.demo.Controller.Actividad;

import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Actividad.ActividadDTO;
import com.example.demo.Service.Actividad.ActividadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apiActividad")
@CrossOrigin
public class ActividadController {

    @Autowired
    private ActividadService service;

    @GetMapping("/getAllActividades")
    private ResponseEntity<Page<ActividadDTO>> getDataActividades(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        if (size <= 0 || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la página debe estar entre 1 y 50"
            ));
            return ResponseEntity.ok(null);
        }
        Page<ActividadDTO> categories = service.getAllActividades(page, size);
        if (categories == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay Actividades registrados"
            ));
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/newActividad")
    private ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody ActividadDTO json, HttpServletRequest request){
        try{
            ActividadDTO response =service.insert(json);
            if (response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "Error", "Inserción incorrecta",
                        "Estatus", "Inserción incorrecta",
                        "Descripción", "Verifique los valores"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Estado", "Completado",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar la actividad",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/updateActividad/{id}")
    public ResponseEntity<?> modificarActividad(
            @PathVariable Long id,
            @Valid @RequestBody ActividadDTO actividad,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            ActividadDTO actividadActualizado = service.update(id, actividad);
            return ResponseEntity.ok(actividadActualizado);
        }
        catch (ExceptionDatoNoEncontrado e){
            return ResponseEntity.notFound().build();
        }
        catch (ExceptionDatoDuplicado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados","campo", e.getCampoDuplicado())
            );
        }
    }

    // Mapea este metodo a una petición DELETE con un parámetro de ruta {id}
    @DeleteMapping("/deleteActividad/{id}")
    public ResponseEntity<Map<String, Object>> eliminarActividad(@PathVariable Long id) {
        try {
            // Intenta eliminar la actividad usando objeto 'service'
            // Si el metodo delete retorna false (no encontró la actividad)
            if (!service.delete(id)) {
                // Retorna una respuesta 404 (Not Found) con información detallada
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        // Agrega un header personalizado
                        .header("X-Mensaje-Error", "Actividad no encontrada")
                        // Cuerpo de la respuesta con detalles del error
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "La actividad no ha sido encontrada",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }

            // Si la eliminación fue exitosa, retorna 200 (OK) con mensaje de confirmación
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Actividad eliminada exitosamente"  // Mensaje de éxito
            ));

        } catch (Exception e) {
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar la actividad",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }
}
