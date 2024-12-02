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
@Table(name = "sugerencia_academico")
public class SugerenciaAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugerenciaAcademico")
    private int idSugerenciaAcademico;

    @Column(name = "estado")
    private String estadoSugerenciaAcademico = "Pendiente"; // Estado por defecto

    @Column(name = "descripcion")
    private String descripcionSugerenciaAcademico;

    @Column(name = "nombre_sugerencia")
    private String nombreSugerenciaAcademico;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacionSugerenciaAcademico = LocalDateTime.now(); // Fecha actual por defecto

    @ManyToOne
    @JoinColumn(name = "id_academico")
    private Academico academico;

    // Getters y Setters
    public int getIdSugerenciaAcademico() {
        return idSugerenciaAcademico;
    }

    public void setIdSugerenciaAcademico(int idSugerenciaAcademico) {
        this.idSugerenciaAcademico = idSugerenciaAcademico;
    }

    public String getEstadoSugerenciaAcademico() {
        return estadoSugerenciaAcademico;
    }

    public void setEstadoSugerenciaAcademico(String estadoSugerenciaAcademico) {
        this.estadoSugerenciaAcademico = estadoSugerenciaAcademico;
    }

    public String getDescripcionSugerenciaAcademico() {
        return descripcionSugerenciaAcademico;
    }

    public void setDescripcionSugerenciaAcademico(String descripcionSugerenciaAcademico) {
        this.descripcionSugerenciaAcademico = descripcionSugerenciaAcademico;
    }

    public String getNombreSugerenciaAcademico() {
        return nombreSugerenciaAcademico;
    }

    public void setNombreSugerenciaAcademico(String nombreSugerenciaAcademico) {
        this.nombreSugerenciaAcademico = nombreSugerenciaAcademico;
    }

    public LocalDateTime getFechaCreacionSugerenciaAcademico() {
        return fechaCreacionSugerenciaAcademico;
    }

    public void setFechaCreacionSugerenciaAcademico(LocalDateTime fechaCreacionSugerenciaAcademico) {
        this.fechaCreacionSugerenciaAcademico = fechaCreacionSugerenciaAcademico;
    }

    public Academico getAcademico() {
        return academico;
    }

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }
}