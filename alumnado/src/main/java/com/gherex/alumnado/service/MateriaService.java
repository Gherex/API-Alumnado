package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Materia;

import java.util.List;

public interface MateriaService {

    Materia saveMateria(Materia materia);
    Materia getMateriaById(Integer id);
    List<Materia> getAllMaterias();
    boolean deleteMateriaById(Integer id);

}