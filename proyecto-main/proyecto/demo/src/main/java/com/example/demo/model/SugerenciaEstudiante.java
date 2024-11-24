package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sugerencia_estudiante")
public class SugerenciaEstudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugerencia")
    private int idSugerenciaEstudiante;

    @Column(name = "estado")
    private String estadoSugerenciaEstudiante = "Pendiente"; // Estado por defecto

    @Column(name = "descripcion")
    private String descripcionSugerenciaEstudiante;

    @Column(name = "nombre_sugerencia")
    private String nombreSugerenciaEstudiante;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacionSugerenciaEstudiante = LocalDateTime.now(); // Fecha actual por defecto

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    // Getters y Setters
    public int getIdSugerenciaEstudiante() {
        return idSugerenciaEstudiante;
    }

    public void setIdSugerenciaEstudiante(int idSugerenciaEstudiante) {
        this.idSugerenciaEstudiante = idSugerenciaEstudiante;
    }

    public String getEstadoSugerenciaEstudiante() {
        return estadoSugerenciaEstudiante;
    }

    public void setEstadoSugerenciaEstudiante(String estadoSugerenciaEstudiante) {
        this.estadoSugerenciaEstudiante = estadoSugerenciaEstudiante;
    }

    public String getDescripcionSugerenciaAcademico() {
        return descripcionSugerenciaEstudiante;
    }

    public void setDescripcionSugerenciaEstudiante(String descripcionSugerenciaEstudiante) {
        this.descripcionSugerenciaEstudiante = descripcionSugerenciaEstudiante;
    }

    public String getNombreSugerenciaEstudiante() {
        return nombreSugerenciaEstudiante;
    }

    public void setNombreSugerenciaEstudiante(String nombreSugerenciaEstudiante) {
        this.nombreSugerenciaEstudiante = nombreSugerenciaEstudiante;
    }

    public LocalDateTime getFechaCreacionSugerenciaEstudiante() {
        return fechaCreacionSugerenciaEstudiante;
    }

    public void setFechaCreacionSugerenciaEstudiante(LocalDateTime fechaCreacionSugerenciaEstudiante) {
        this.fechaCreacionSugerenciaEstudiante = fechaCreacionSugerenciaEstudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}