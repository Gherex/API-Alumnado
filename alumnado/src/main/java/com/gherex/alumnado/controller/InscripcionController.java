package com.gherex.alumnado.controller;

import com.gherex.alumnado.entity.Inscripcion;
import com.gherex.alumnado.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @Autowired
    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // Obtener todas las inscripciones
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // Permite a ADMIN y USER ver todas las inscripciones
    public ResponseEntity<List<Inscripcion>> getAllInscripciones() {
        List<Inscripcion> inscripciones = inscripcionService.getAllInscripciones();
        if (inscripciones.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay inscripciones
        }
        return ResponseEntity.ok(inscripciones); // HTTP 200 con la lista de inscripciones
    }

    // Obtener una inscripción por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // Permite a ADMIN y USER ver una inscripción
    public ResponseEntity<?> getInscripcionById(@PathVariable Integer id) {
        Inscripcion inscripcion = inscripcionService.getInscripcionById(id);
        if (inscripcion != null) {
            return ResponseEntity.ok(inscripcion); // HTTP 200 con la inscripción encontrada
        } else {
            return ResponseEntity.status(404).body("Error: Inscripción no encontrada con ID: " + id); // HTTP 404 NOT FOUND
        }
    }

    // Crear una nueva inscripción
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede crear una nueva inscripción
    public ResponseEntity<Inscripcion> saveInscripcion(@RequestBody Inscripcion inscripcion, UriComponentsBuilder uriComponentsBuilder) {
        // Guardar la inscripción
        Inscripcion savedInscripcion = inscripcionService.saveInscripcion(inscripcion);

        // Crear la URI del nuevo recurso
        URI location = uriComponentsBuilder.path("/inscripciones/{id}")
                .buildAndExpand(savedInscripcion.getId_inscripcion())
                .toUri();

        // Retornar la respuesta con el código HTTP 201 y la URI del nuevo recurso en el encabezado Location
        return ResponseEntity.created(location).body(savedInscripcion);
    }

    // Actualizar una inscripción existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede actualizar una inscripción
    public ResponseEntity<?> updateInscripcion(@PathVariable Integer id, @RequestBody Inscripcion inscripcion) {
        Inscripcion existingInscripcion = inscripcionService.getInscripcionById(id);
        if (existingInscripcion != null) {
            Inscripcion updatedInscripcion = inscripcionService.saveInscripcion(inscripcion);
            return ResponseEntity.ok(updatedInscripcion); // HTTP 200 con la inscripción actualizada
        } else {
            return ResponseEntity.status(404).body("Error: Inscripción no encontrada con ID: " + id); // HTTP 404 NOT FOUND
        }
    }

    // Eliminar una inscripción
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede eliminar una inscripción
    public ResponseEntity<?> deleteInscripcionById(@PathVariable Integer id) {
        boolean eliminado = inscripcionService.deleteInscripcionById(id);
        if (eliminado) {
            return ResponseEntity.ok("Inscripción eliminada con éxito."); // HTTP 200
        } else {
            return ResponseEntity.status(404).body("Error: Inscripción no encontrada con ID: " + id); // HTTP 404 NOT FOUND
        }
    }
}
