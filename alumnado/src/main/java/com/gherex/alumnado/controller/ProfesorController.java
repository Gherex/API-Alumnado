package com.gherex.alumnado.controller;

import com.gherex.alumnado.entity.Profesor;
import com.gherex.alumnado.service.ProfesorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    @Autowired
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // Obtener todos los profesores
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // Permite a ADMIN y USER ver todos los profesores
    public ResponseEntity<List<Profesor>> getAllProfesores() {
        List<Profesor> profesores = profesorService.getAllProfesores();
        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay profesores
        }
        return ResponseEntity.ok(profesores); // HTTP 200 con la lista de profesores
    }

    // Obtener un profesor por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // Permite a ADMIN y USER ver un profesor
    public ResponseEntity<?> getProfesorById(@PathVariable Integer id) {
        Profesor profesor = profesorService.getProfesorById(id);
        if (profesor != null) {
            return ResponseEntity.ok(profesor); // HTTP 200 con el profesor encontrado
        } else {
            return ResponseEntity.status(404).body("Error: Profesor no encontrado con ID: " + id); // HTTP 404 NOT FOUND
        }
    }

    // Crear un nuevo profesor
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede crear un nuevo profesor
    public ResponseEntity<Profesor> saveProfesor(@RequestBody Profesor profesor, UriComponentsBuilder uriComponentsBuilder) {
        Profesor savedProfesor = profesorService.saveProfesor(profesor);

        URI location = uriComponentsBuilder.path("/profesores/{id}")
                .buildAndExpand(savedProfesor.getId_profesor())
                .toUri();

        return ResponseEntity.created(location).body(savedProfesor); // HTTP 201 con la URI del nuevo recurso
    }

    // Actualizar un profesor existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede actualizar un profesor
    public ResponseEntity<?> updateProfesor(@PathVariable Integer id, @RequestBody Profesor profesor) {
        Profesor existingProfesor = profesorService.getProfesorById(id);
        if (existingProfesor != null) {
            profesor.getPersona().setId_persona(existingProfesor.getPersona().getId_persona()); // Mantener el ID de la persona original
            Profesor updatedProfesor = profesorService.saveProfesor(profesor);
            return ResponseEntity.ok(updatedProfesor); // HTTP 200 con el profesor actualizado
        } else {
            return ResponseEntity.status(404).body("Error: Profesor no encontrado con ID: " + id); // HTTP 404 NOT FOUND
        }
    }

    // Eliminar un profesor
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede eliminar un profesor
    public ResponseEntity<?> deleteProfesorById(@PathVariable Integer id) {
        boolean eliminado = profesorService.deleteProfesorById(id);
        if (eliminado) {
            return ResponseEntity.ok("Profesor eliminado con éxito."); // HTTP 200
        } else {
            return ResponseEntity.status(404).body("Error: Profesor no encontrado con ID: " + id); // HTTP 404 NOT FOUND
        }
    }
}
