package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Polo;
import com.example.demo.model.Proyectos;
import com.example.demo.repository.ProyectosRepository;

@Service
public class ProyectosServiceImpl implements ProyectosService {

    @Autowired
    private ProyectosRepository proyectosRepository;

    @Override
    public Proyectos crearProyecto(Proyectos proyecto, Polo polo) {
        proyecto.setPolo(polo);
        return proyectosRepository.save(proyecto);
    }

    @Override
    public List<Proyectos> obtenerProyectosPorPolo(Polo polo) {
        return proyectosRepository.findByPolo(polo);
    }

    @Override
    public List<Proyectos> obtenerTodosLosProyectos() {
        return proyectosRepository.findAll();
    }
}