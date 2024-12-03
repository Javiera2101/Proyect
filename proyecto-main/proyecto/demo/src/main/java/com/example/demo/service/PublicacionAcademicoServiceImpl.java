package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Academico;
import com.example.demo.model.PublicacionAcademico;
import com.example.demo.repository.PublicacionAcademicoRepository;

@Service
public class PublicacionAcademicoServiceImpl implements PublicacionAcademicoService {

    @Autowired
    private PublicacionAcademicoRepository publicacionRepository;

    @Override
    public PublicacionAcademico crearPublicacion(PublicacionAcademico publicacion, Academico academico) {
        publicacion.setAcademico(academico);
        return publicacionRepository.save(publicacion);
    }

    @Override
    public List<PublicacionAcademico> obtenerPublicacionesPorAcademico(Academico academico) {
        return publicacionRepository.findAll().stream()
            .filter(publicacion -> publicacion.getAcademico().equals(academico))
            .toList();
    }

    @Override
    public List<PublicacionAcademico> obtenerTodasLasPublicaciones() {
        return publicacionRepository.findAll();
    }
}