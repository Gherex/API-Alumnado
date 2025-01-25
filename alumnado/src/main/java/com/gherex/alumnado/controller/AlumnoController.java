package com.gherex.alumnado.controller;

import com.gherex.alumnado.entity.Alumno;
import com.gherex.alumnado.service.AlumnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // Obtener todos los alumnos
    @GetMapping
    public ResponseEntity<List<Alumno>> getAllAlumnos() {
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay alumnos
        }
        return ResponseEntity.ok(alumnos); // HTTP 200 con la lista de alumnos
    }

    // Obtener un alumno por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlumnoById(@PathVariable Integer id) {
        Alumno alumno = alumnoService.getAlumnoById(id);
        if (alumno != null) {
            return ResponseEntity.ok(alumno); // HTTP 200 con el alumno encontrado
        } else {
            return ResponseEntity.status(404).body("Error: Alumno no encontrado con ID: " + id); // HTTP 404
        }
    }

    // Crear un nuevo alumno
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede crear un nuevo alumno
    public ResponseEntity<Alumno> saveAlumno(@RequestBody Alumno alumno, UriComponentsBuilder uriComponentsBuilder) {
        // Guardar el alumno
        Alumno savedAlumno = alumnoService.saveAlumno(alumno);

        // Crear la URI del nuevo recurso
        URI location = uriComponentsBuilder.path("/alumnos/{id}")
                .buildAndExpand(savedAlumno.getId_alumno())
                .toUri();

        // Retornar la respuesta con el código HTTP 201 y la URI del nuevo recurso en el encabezado Location
        return ResponseEntity.created(location).body(savedAlumno);
    }

    // Actualizar un alumno existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede actualizar un alumno
    public ResponseEntity<?> updateAlumno(@PathVariable Integer id, @RequestBody Alumno alumno) {
        Alumno existingAlumno = alumnoService.getAlumnoById(id);
        if (existingAlumno != null) {
            alumno.getPersona().setId_persona(existingAlumno.getPersona().getId_persona()); // Mantener el ID de la persona original
            Alumno updatedAlumno = alumnoService.saveAlumno(alumno);
            return ResponseEntity.ok(updatedAlumno); // HTTP 200 con el alumno actualizado
        } else {
            return ResponseEntity.status(404).body("Error: Alumno no encontrado con ID: " + id); // HTTP 404
        }
    }

    // Eliminar un alumno
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede eliminar un alumno
    public ResponseEntity<?> deleteAlumnoById(@PathVariable Integer id) {
        boolean eliminado = alumnoService.deleteAlumnoById(id);
        if (eliminado) {
            return ResponseEntity.ok("Alumno eliminado con éxito."); // HTTP 200
        } else {
            return ResponseEntity.status(404).body("Error: Alumno no encontrado con ID: " + id); // HTTP 404
        }
    }
}