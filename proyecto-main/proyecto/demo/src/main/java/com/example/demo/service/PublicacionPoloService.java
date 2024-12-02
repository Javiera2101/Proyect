package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Polo;
import com.example.demo.model.PublicacionPolo;

public interface PublicacionPoloService {
    PublicacionPolo crearPublicacion(PublicacionPolo publicacion, Polo polo);
    List<PublicacionPolo> obtenerPublicacionesPorPolo(Polo polo);
}