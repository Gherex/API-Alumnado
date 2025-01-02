package com.gherex.alumnado.repository;

import com.gherex.alumnado.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
