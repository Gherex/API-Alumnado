package com.gherex.alumnado.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Integer id_alumno;

    @OneToOne(cascade = CascadeType.ALL) // Relación de uno a uno con Persona
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona; // Composición de Persona

    @Column(name = "matricula", unique = true, nullable = false, length = 20)
    private String matricula;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso", nullable = false)
    private Date fecha_ingreso;

    public Alumno() {}

    // Constructor
    public Alumno(Persona persona, String matricula, Date fecha_ingreso) {
        this.persona = persona;
        this.matricula = matricula;
        this.fecha_ingreso = fecha_ingreso;
    }

    // Getters y setters
    public Integer getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(Integer id_alumno) {
        this.id_alumno = id_alumno;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
}

