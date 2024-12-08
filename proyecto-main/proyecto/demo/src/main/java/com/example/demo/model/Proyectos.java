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
@Table(name = "proyecto")
public class Proyectos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private int idProyecto;

    @Column(name = "tipo")
    private String tipoProyecto;

    @Column(name = "estado_proyecto")
    private String estadoProyecto;

    @Column(name = "fecha_inicio")
    private LocalDateTime FechaInicioProyecto;

    @Column(name = "fecha_fin")
    private LocalDateTime FechaFinProyecto;

    @Column(name = "nombre")
    private String nombreProyecto;

    @Column(name = "Descripcion_proyecto")
    private String descripcionProyecto;

    @Column(name = "imagen_url_proyecto")
    private String imagenUrlProyecto;

    @ManyToOne
    @JoinColumn(name = "id_polo")
    private Polo polo;

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public LocalDateTime getFechaInicioProyecto() {
        return FechaInicioProyecto;
    }

    public void setFechaInicioProyecto(LocalDateTime fechaInicioProyecto) {
        FechaInicioProyecto = fechaInicioProyecto;
    }

    public LocalDateTime getFechaFinProyecto() {
        return FechaFinProyecto;
    }

    public void setFechaFinProyecto(LocalDateTime fechaFinProyecto) {
        FechaFinProyecto = fechaFinProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getImagenUrlProyecto() {
        return imagenUrlProyecto;
    }

    public void setImagenUrlProyecto(String imagenUrlProyecto) {
        this.imagenUrlProyecto = imagenUrlProyecto;
    }

    public Polo getPolo() {
        return polo;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }

    
}
