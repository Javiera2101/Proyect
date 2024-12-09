package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Academico;
import com.example.demo.model.SugerenciaAcademico;
import com.example.demo.repository.SugerenciaAcademicoRepository;

@Service
public class SugerenciaAcademicoServiceImpl implements SugerenciaAcademicoService {

    @Autowired
    private SugerenciaAcademicoRepository sugerenciaRepository;

    @Override
    public SugerenciaAcademico crearSugerencia(SugerenciaAcademico sugerencia, Academico academico) {
        sugerencia.setAcademico(academico);
        return sugerenciaRepository.save(sugerencia);
    }

    @Override
    public List<SugerenciaAcademico> obtenerSugerenciasPorAcademico(Academico academico) {
        return sugerenciaRepository.findByAcademico(academico);
    }

    @Override
    public List<SugerenciaAcademico> obtenerSugerenciasPendientes() {
        return sugerenciaRepository.findByEstadoSugerenciaAcademico("Pendiente");
    }

    @Override
    public SugerenciaAcademico actualizarEstadoSugerencia(int idSugerencia, String nuevoEstado) {
        SugerenciaAcademico sugerencia = sugerenciaRepository.findById(idSugerencia)
            .orElseThrow(() -> new RuntimeException("Sugerencia no encontrada"));
        
        sugerencia.setEstadoSugerenciaAcademico(nuevoEstado);
        return sugerenciaRepository.save(sugerencia);
    }
}