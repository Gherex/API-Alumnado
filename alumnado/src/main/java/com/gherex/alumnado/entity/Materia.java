package com.gherex.alumnado.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Integer id_materia;

    // Relación muchos a uno con la tabla Profesor
    @ManyToOne
    @JoinColumn(name = "id_profesor", referencedColumnName = "id_profesor")
    private Profesor profesor;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    public Materia() {}

    public Materia(Integer id_materia, Profesor profesor, String nombre) {
        this.id_materia = id_materia;
        this.profesor = profesor;
        this.nombre = nombre;
    }

    public Integer getId_materia() {
        return id_materia;
    }

    public void setId_materia(Integer id_materia) {
        this.id_materia = id_materia;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
