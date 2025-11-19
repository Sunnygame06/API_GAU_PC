package com.example.demo.Controller.Usuario;

import com.example.demo.Exceptions.DatoDuplicado.ExceptionDatoDuplicado;
import com.example.demo.Exceptions.DatoNoEncontrado.ExceptionDatoNoEncontrado;
import com.example.demo.Models.DTO.Usuario.UsuarioDTO;
import com.example.demo.Service.Usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/apiUsuario")
@CrossOrigin(origins = "*") // permite CORS desde cualquier origen (solo para desarrollo)
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // 🔹 GET ALL USUARIOS
    @GetMapping("/getAllUsuarios")
    public ResponseEntity<?> getAllUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (size <= 0 || size > 50) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "El tamaño de la página debe estar entre 1 y 50"
            ));
        }

        Page<UsuarioDTO> usuarios = service.getAllUsuarios(page, size);

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "content", new Object[]{},
                    "empty", true
            ));
        }

        return ResponseEntity.ok(usuarios);
    }

    // 🔹 GET USUARIO POR ID
    @GetMapping("/getUsuario/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = service.getUsuarioById(id);
            return ResponseEntity.ok(usuario);
        } catch (ExceptionDatoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
        }
    }

    // 🔹 CREAR USUARIO
    @PostMapping("/newUsuario")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDTO json) {
        try {
            UsuarioDTO response = service.insert(json);
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

    // 🔹 ACTUALIZAR USUARIO
    @PutMapping("/updateUsuario/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuario) {
        try {
            UsuarioDTO usuarioActualizado = service.update(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (ExceptionDatoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        } catch (ExceptionDatoDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado()
            ));
        }
    }

    // 🔹 ELIMINAR USUARIO
    @DeleteMapping("/deleteUsuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            if (!service.delete(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "error", "Usuario no encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", "Completado",
                    "message", "Usuario eliminado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", e.getMessage()
            ));
        }
    }
}
