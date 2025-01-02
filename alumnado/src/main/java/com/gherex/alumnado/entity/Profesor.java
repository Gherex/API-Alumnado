package com.gherex.alumnado.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Integer id_profesor;

    @OneToOne(cascade = CascadeType.ALL) // Relación de uno a uno con Persona
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona; // Composición de Persona

    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_contratacion", nullable = false)
    private Date fecha_contratacion;

    public Profesor() {}

    public Profesor(Persona persona, String especialidad, Date fecha_contratacion) {
        this.persona = persona;
        this.especialidad = especialidad;
        this.fecha_contratacion = fecha_contratacion;
    }

    public Integer getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(Integer id_profesor) {
        this.id_profesor = id_profesor;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(Date fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }
}
