package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Publicacion { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion; // Cambiar a camelCase

    private String nomPublicacion; // Cambiar a camelCase
    private String gestionar;
    private String categoria;
    private LocalDate fechaPublicacion;

    // Relación con Polo (muchos a uno)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "polo_id")
    private Polo polo;

    // Constructor vacío requerido por JPA
    public Publicacion() {
    }

    // Constructor con parámetros opcionales
    public Publicacion(String nomPublicacion, String gestionar, String categoria, Polo polo) {
        this.nomPublicacion = nomPublicacion;
        this.gestionar = gestionar;
        this.categoria = categoria;
        this.fechaPublicacion = LocalDate.now(); // La fecha será el día actual
        this.polo = polo;
    }

    // Getters y Setters
    public Long getIdPublicacion() { // Cambiar a camelCase
        return idPublicacion;
    }

    public void setIdPublicacion(Long idPublicacion) { // Cambiar a camelCase
        this.idPublicacion = idPublicacion;
    }

    public String getNomPublicacion() { // Cambiar a camelCase
        return nomPublicacion;
    }

    public void setNomPublicacion(String nomPublicacion) { // Cambiar a camelCase
        this.nomPublicacion = nomPublicacion;
    }

    public String getGestionar() {
        return gestionar;
    }

    public void setGestionar(String gestionar) {
        this.gestionar = gestionar;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Polo getPolo() {
        return polo;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }
}
