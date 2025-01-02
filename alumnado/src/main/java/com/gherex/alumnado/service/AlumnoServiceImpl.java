package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Alumno;
import com.gherex.alumnado.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        // save method sigue funcionando igual
        return alumnoRepository.save(alumno);
    }

    @Override
    public Alumno getAlumnoById(Integer id) {
        // Si no se encuentra el alumno, se devuelve null
        return alumnoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Alumno> getAllAlumnos() {
        // Obtener todos los alumnos
        return alumnoRepository.findAll();
    }

    @Override
    public boolean deleteAlumnoById(Integer id) {
        // Verifica si el alumno existe antes de eliminarlo
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            return true; // Se eliminó correctamente
        }
        return false; // No se encontró el alumno
    }
}
