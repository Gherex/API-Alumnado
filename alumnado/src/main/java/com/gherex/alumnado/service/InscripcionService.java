package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Inscripcion;

import java.util.List;

public interface InscripcionService {

    Inscripcion saveInscripcion(Inscripcion inscripcion);
    Inscripcion getInscripcionById(Integer id);
    List<Inscripcion> getAllInscripciones();
    boolean deleteInscripcionById(Integer id);

}
