package com.gherex.alumnado.service;

import com.gherex.alumnado.entity.Materia;
import com.gherex.alumnado.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public Materia saveMateria(Materia materia){
        return materiaRepository.save(materia);
    }

    @Override
    public Materia getMateriaById(Integer id) {
        return materiaRepository.findById(id).orElse(null); // Devuelve null si no encuentra el alumno
    }

    @Override
    public List<Materia> getAllMaterias(){
        return materiaRepository.findAll();
    }

    @Override
    public boolean deleteMateriaById(Integer id) {
        if (materiaRepository.existsById(id)) {
            materiaRepository.deleteById(id); // Elimina por ID
            return true; // Indica que se eliminó exitosamente
        }
        return false; // Indica que no se encontró el alumno
    }

}
