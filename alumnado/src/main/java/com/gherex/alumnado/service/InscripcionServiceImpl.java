package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Inscripcion;
import com.gherex.alumnado.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    public Inscripcion saveInscripcion(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion); // Usa el save method del repositorio
    }

    @Override
    public Inscripcion getInscripcionById(Integer id) {
        return inscripcionRepository.findById(id).orElse(null); // Devuelve null si no encuentra el alumno
    }

    @Override
    public List<Inscripcion> getAllInscripciones() {
        return inscripcionRepository.findAll(); // Devuelve todas las inscripciones
    }

    @Override
    public boolean deleteInscripcionById(Integer id) {
        if (inscripcionRepository.existsById(id)) {
            inscripcionRepository.deleteById(id); // Elimina por ID
            return true; // Indica que se eliminó exitosamente
        }
        return false; // Indica que no se encontró el alumno
    }
}
