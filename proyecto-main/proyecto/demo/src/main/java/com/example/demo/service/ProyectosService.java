package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Polo;
import com.example.demo.model.Proyectos;

public interface ProyectosService {
    Proyectos crearProyecto(Proyectos proyecto, Polo polo);
    List<Proyectos> obtenerProyectosPorPolo(Polo polo);
    List<Proyectos> obtenerTodosLosProyectos();
}