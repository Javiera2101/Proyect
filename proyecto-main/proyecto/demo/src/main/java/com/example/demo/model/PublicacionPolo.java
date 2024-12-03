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
@Table(name = "publicacion_polo")
public class PublicacionPolo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_noticia_polo")
    private int idNoticiaPolo;

    @Column(name = "nom_noticia")
    private String nomNoticiaPolo;

    @Column(name = "Descripcion_noticia")
    private String descripcionNoticiaPolo;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacionNoticiaPolo;

    @Column(name = "categoria")
    private String categoriaNoticiaPolo;

    @ManyToOne
    @JoinColumn(name = "id_polo")
    private Polo polo;

    public int getIdNoticiaPolo() {
        return idNoticiaPolo;
    }

    public void setIdNoticiaPolo(int idNoticiaPolo) {
        this.idNoticiaPolo = idNoticiaPolo;
    }

    public String getNomNoticiaPolo() {
        return nomNoticiaPolo;
    }

    public void setNomNoticiaPolo(String nomNoticiaPolo) {
        this.nomNoticiaPolo = nomNoticiaPolo;
    }

    public String getDescripcionNoticiaPolo() {
        return descripcionNoticiaPolo;
    }

    public void setDescripcionNoticiaPolo(String descripcionNoticiaPolo) {
        this.descripcionNoticiaPolo = descripcionNoticiaPolo;
    }

    public LocalDateTime getFechaPublicacionNoticiaPolo() {
        return fechaPublicacionNoticiaPolo;
    }

    public void setFechaPublicacionNoticiaPolo(LocalDateTime fechaPublicacionNoticiaPolo) {
        this.fechaPublicacionNoticiaPolo = fechaPublicacionNoticiaPolo;
    }

    public String getCategoriaNoticiaPolo() {
        return categoriaNoticiaPolo;
    }

    public void setCategoriaNoticiaPolo(String categoriaNoticiaPolo) {
        this.categoriaNoticiaPolo = categoriaNoticiaPolo;
    }

    public Polo getPolo() {
        return polo;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }

    
}
