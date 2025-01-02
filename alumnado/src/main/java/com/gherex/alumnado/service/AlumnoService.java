package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Alumno;

import java.util.List;

public interface AlumnoService {

    Alumno saveAlumno(Alumno alumno);  // Crear o actualizar un alumno
    Alumno getAlumnoById(Integer id);   // Buscar un alumno por su ID
    List<Alumno> getAllAlumnos();     // Obtener todos los alumnos
    boolean deleteAlumnoById(Integer id);  // Eliminar un alumno por su ID
}
