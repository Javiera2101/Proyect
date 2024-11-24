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
@Table(name = "sugerencia_estudiante")
public class SugerenciaEstudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugerencia")
    private int idSugerenciaEstudiante;

    @Column(name = "estado")
    private String estadoSugerenciaEstudiante = "Pendiente"; // Estado por defecto

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    @Column(name = "descripcion")
    private String descripcionSugerenciaEstudiante;

    @NotBlank(message = "El nombre de la sugerencia no puede estar vacío")
    @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
    @Column(name = "nombre_sugerencia")
    private String nombreSugerenciaEstudiante;

    // Cambiar el nombre de la columna para que coincida con la base de datos
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