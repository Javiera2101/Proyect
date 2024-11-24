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
        return sugerenciaRepository.findAll().stream()
            .filter(sugerencia -> sugerencia.getAcademico().equals(academico))
            .toList();
    }
}