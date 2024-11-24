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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sugerencia_academico")
public class SugerenciaAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugerencia")
    private int idSugerenciaAcademico;

    @Column(name = "estado")
    private String estadoSugerenciaAcademico = "Pendiente"; // Estado por defecto

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    @Column(name = "descripcion")
    private String descripcionSugerenciaAcademico;

    @NotBlank(message = "El nombre de la sugerencia no puede estar vacío")
    @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
    @Column(name = "nombre_sugerencia")
    private String nombreSugerenciaAcademico;

    // Cambiar el nombre de la columna para que coincida con la base de datos
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