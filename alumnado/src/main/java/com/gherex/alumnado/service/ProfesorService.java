package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Profesor;

import java.util.List;

public interface ProfesorService {

    Profesor saveProfesor(Profesor profesor);
    Profesor getProfesorById(Integer id);
    List<Profesor> getAllProfesores();
    boolean deleteProfesorById(Integer id);

}