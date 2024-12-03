package com.example.demo.model;

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
    private String FechaInicioProyecto;

    @Column(name = "fecha_fin")
    private String FechaFinProyecto;

    @Column(name = "nombre")
    private String nombreProyecto;

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

    public String getFechaInicioProyecto() {
        return FechaInicioProyecto;
    }

    public void setFechaInicioProyecto(String fechaInicioProyecto) {
        FechaInicioProyecto = fechaInicioProyecto;
    }

    public String getFechaFinProyecto() {
        return FechaFinProyecto;
    }

    public void setFechaFinProyecto(String fechaFinProyecto) {
        FechaFinProyecto = fechaFinProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public Polo getPolo() {
        return polo;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }

    
    
}
