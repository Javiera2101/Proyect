package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Academico;
import com.example.demo.model.PublicacionAcademico;

public interface PublicacionAcademicoService {
    PublicacionAcademico crearPublicacion(PublicacionAcademico publicacion, Academico academico);
    List<PublicacionAcademico> obtenerPublicacionesPorAcademico(Academico academico);
    List<PublicacionAcademico> obtenerTodasLasPublicaciones();
    void eliminarPublicacion(int id);
    PublicacionAcademico obtenerPublicacionPorId(int id);
    void actualizarPublicacion(PublicacionAcademico publicacion);
}
