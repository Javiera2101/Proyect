package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Polo;
import com.example.demo.model.PublicacionPolo;
import com.example.demo.repository.PublicacionPoloRepository;

@Service
public class PublicacionPoloServiceImpl implements PublicacionPoloService {

    @Autowired
    private PublicacionPoloRepository publicacionRepository;

    @Override
    public PublicacionPolo crearPublicacion(PublicacionPolo publicacion, Polo polo) {
        publicacion.setPolo(polo);
        return publicacionRepository.save(publicacion);
    }

    @Override
    public List<PublicacionPolo> obtenerPublicacionesPorPolo(Polo polo) {
        return publicacionRepository.findByPolo(polo);
    }
}