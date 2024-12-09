package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Academico;
import com.example.demo.model.SugerenciaAcademico;

public interface SugerenciaAcademicoService {
    SugerenciaAcademico crearSugerencia(SugerenciaAcademico sugerencia, Academico academico);
    List<SugerenciaAcademico> obtenerSugerenciasPorAcademico(Academico academico);
    List<SugerenciaAcademico> obtenerSugerenciasPendientes();
    SugerenciaAcademico actualizarEstadoSugerencia(int idSugerencia, String nuevoEstado);
}