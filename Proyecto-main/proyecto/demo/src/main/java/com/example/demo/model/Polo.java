package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "polo")
public class Polo {
    @Column(name = "id_polo")
    private int idPolo;

    @Column(name = "nombre_polo")
    private String nombrePolo;

    @Column(name = "correo_polo")
    private String correoPolo;

    @Column(name = "contrasena_polo")
    private String contrasenaPolo;

    @Column(name = "num_telefono")
    private int numTelefono;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdPolo() {
        return idPolo;
    }
    public void setIdPolo(int idPolo) {
        this.idPolo = idPolo;
    }
    public String getNombrePolo() {
        return nombrePolo;
    }
    public void setNombrePolo(String nombrePolo) {
        this.nombrePolo = nombrePolo;
    }
    public String getCorreoPolo() {
        return correoPolo;
    }
    public void setCorreoPolo(String correoPolo) {
        this.correoPolo = correoPolo;
    }
    public String getContrasenaPolo() {
        return contrasenaPolo;
    }
    public void setContrasenaPolo(String contrasenaPolo) {
        this.contrasenaPolo = contrasenaPolo;
    }
    public int getNumTelefono() {
        return numTelefono;
    }
    public void setNumTelefono(int numTelefono) {
        this.numTelefono = numTelefono;
    }

    
}
