package com.gherex.alumnado.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Integer id_inscripcion;

    // Relación muchos a uno con Alumno
    @ManyToOne
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno", nullable = false)
    private Alumno alumno;

    // Relación muchos a uno con Materia
    @ManyToOne
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia", nullable = false)
    private Materia materia;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inscripcion", nullable = false)
    private Date fecha_inscripcion;

    public Inscripcion() {}

    public Inscripcion(Alumno alumno, Materia materia, Date fecha_inscripcion) {
        this.alumno = alumno;
        this.materia = materia;
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public Integer getId_inscripcion() {
        return id_inscripcion;
    }

    public void setId_inscripcion(Integer id_inscripcion) {
        this.id_inscripcion = id_inscripcion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Date getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(Date fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }
}
