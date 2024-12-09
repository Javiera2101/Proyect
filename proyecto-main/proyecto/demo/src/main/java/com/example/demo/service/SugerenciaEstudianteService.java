package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Estudiante;
import com.example.demo.model.SugerenciaEstudiante;

public interface SugerenciaEstudianteService {
    SugerenciaEstudiante crearSugerencia(SugerenciaEstudiante sugerencia, Estudiante estudiante);
    List<SugerenciaEstudiante> obtenerSugerenciasPorEstudiante(Estudiante estudiante);

    public List<SugerenciaEstudiante> obtenerTodasSugerencias();

    public SugerenciaEstudiante actualizarEstadoSugerencia(int idSugerencia, String nuevoEstado);
}