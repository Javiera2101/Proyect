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
@Table(name = "publicacion_academico")
public class PublicacionAcademico {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_noticiaAcademico")
    private int idNoticiaAcademico;

    @Column(name = "nom_noticia")
    private String nomNoticiaAcademico;

    @Column(name = "Descripcion_noticia")
    private String descripcionNoticiaAcademico;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacionNoticiaAcademico = LocalDateTime.now();

    @Column(name = "categoria")
    private String categoriaNoticiaAcademico;

    @ManyToOne
    @JoinColumn(name = "id_academico")
    private Academico academico;

    public int getIdNoticiaAcademico() {
        return idNoticiaAcademico;
    }

    public void setIdNoticiaAcademico(int idNoticiaAcademico) {
        this.idNoticiaAcademico = idNoticiaAcademico;
    }

    public String getNomNoticiaAcademico() {
        return nomNoticiaAcademico;
    }

    public void setNomNoticiaAcademico(String nomNoticiaAcademico) {
        this.nomNoticiaAcademico = nomNoticiaAcademico;
    }

    public String getDescripcionNoticiaAcademico() {
        return descripcionNoticiaAcademico;
    }

    public void setDescripcionNoticiaAcademico(String descripcionNoticiaAcademico) {
        this.descripcionNoticiaAcademico = descripcionNoticiaAcademico;
    }

    public LocalDateTime getFechaPublicacionNoticiaAcademico() {
        return fechaPublicacionNoticiaAcademico;
    }

    public void setFechaPublicacionNoticiaAcademico(LocalDateTime fechaPublicacionNoticiaAcademico) {
        this.fechaPublicacionNoticiaAcademico = fechaPublicacionNoticiaAcademico;
    }

    public String getCategoriaNoticiaAcademico() {
        return categoriaNoticiaAcademico;
    }

    public void setCategoriaNoticiaAcademico(String categoriaNoticiaAcademico) {
        this.categoriaNoticiaAcademico = categoriaNoticiaAcademico;
    }

    public Academico getAcademico() {
        return academico;
    }

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }

    
}
