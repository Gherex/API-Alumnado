package com.gherex.alumnado.controller;

import com.gherex.alumnado.entity.Materia;
import com.gherex.alumnado.service.MateriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;

    @Autowired
    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    // Obtener todas las materias
    @GetMapping
    public ResponseEntity<List<Materia>> getAllMaterias() {
        List<Materia> materias = materiaService.getAllMaterias();
        if (materias.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay materias
        }
        return ResponseEntity.ok(materias); // HTTP 200 con la lista de materias
    }

    // Obtener una materia por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMateriaById(@PathVariable Integer id) {
        Materia materia = materiaService.getMateriaById(id);
        if (materia != null) {
            return ResponseEntity.ok(materia); // HTTP 200 con la materia encontrada
        } else {
            return ResponseEntity.status(404).body("Error: Materia no encontrada con ID: " + id); // HTTP 404 NOT FOUND
        }
    }

    // Crear una nueva materia
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede crear una nueva materia
    public ResponseEntity<Materia> saveMateria(@RequestBody Materia materia, UriComponentsBuilder uriComponentsBuilder) {
        Materia savedMateria = materiaService.saveMateria(materia);

        URI location = uriComponentsBuilder.path("/materias/{id}")
                .buildAndExpand(savedMateria.getId_materia())
                .toUri();

        return ResponseEntity.created(location).body(savedMateria); // HTTP 201 con la URI del nuevo recurso
    }

    // Actualizar una materia existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede actualizar una materia
    public ResponseEntity<?> updateMateria(@PathVariable Integer id, @RequestBody Materia materia) {
        Materia existingMateria = materiaService.getMateriaById(id);
        if (existingMateria != null) {
            Materia updatedMateria = materiaService.saveMateria(materia);
            return ResponseEntity.ok(updatedMateria); // HTTP 200 con la materia actualizada
        } else {
            return ResponseEntity.status(404).body("Error: Materia no encontrada con ID: " + id); // HTTP 404 NOT FOUND
        }
    }

    // Eliminar una materia
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede eliminar una materia
    public ResponseEntity<?> deleteMateriaById(@PathVariable Integer id) {
        boolean eliminado = materiaService.deleteMateriaById(id);
        if (eliminado) {
            return ResponseEntity.ok("Materia eliminada con éxito."); // HTTP 200
        } else {
            return ResponseEntity.status(404).body("Error: Materia no encontrada con ID: " + id); // HTTP 404 NOT FOUND
        }
    }
}
