package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Sugerencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sugerencia;

    private String nombre_sugerencia;
    private String descripcion;
    private String editar;
    private String estado;

    // Relación muchos-a-uno con Academico (una sugerencia puede ser escrita por un solo academico)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academico_id", nullable = true)
    private Academico academico;

    // Relación muchos-a-uno con Estudiante (una sugerencia puede ser escrita por un solo estudiante)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = true)
    private Estudiante estudiante;

    // Relación muchos-a-uno con Polo (una sugerencia es gestionada por un solo Polo)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "polo_id", nullable = false)
    private Polo polo;

    // Constructor vacío
    public Sugerencia() {}

    // Constructor con parámetros
    public Sugerencia(String nombre_sugerencia, String descripcion, String editar, String estado, Academico academico, Estudiante estudiante, Polo polo) {
        this.nombre_sugerencia = nombre_sugerencia;
        this.descripcion = descripcion;
        this.editar = editar;
        this.estado = estado;
        this.academico = academico;
        this.estudiante = estudiante;
        this.polo = polo;
    }

    // Getters y Setters

    public Long getId_sugerencia() {
        return id_sugerencia;
    }

    public void setId_sugerencia(Long id_sugerencia) {
        this.id_sugerencia = id_sugerencia;
    }

    public String getNombre_sugerencia() {
        return nombre_sugerencia;
    }

    public void setNombre_sugerencia(String nombre_sugerencia) {
        this.nombre_sugerencia = nombre_sugerencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEditar() {
        return editar;
    }

    public void setEditar(String editar) {
        this.editar = editar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Academico getAcademico() {
        return academico;
    }

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Polo getPolo() {
        return polo;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }
}

