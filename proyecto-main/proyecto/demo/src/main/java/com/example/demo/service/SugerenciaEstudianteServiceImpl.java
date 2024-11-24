package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Estudiante;
import com.example.demo.model.SugerenciaEstudiante;
import com.example.demo.repository.SugerenciaEstudianteRepository;

@Service
public class SugerenciaEstudianteServiceImpl implements SugerenciaEstudianteService {

    @Autowired
    private SugerenciaEstudianteRepository sugerenciaRepository;

    @Override
    public SugerenciaEstudiante crearSugerencia(SugerenciaEstudiante sugerencia, Estudiante estudiante) {
        sugerencia.setEstudiante(estudiante);
        return sugerenciaRepository.save(sugerencia);
    }

    @Override
    public List<SugerenciaEstudiante> obtenerSugerenciasPorEstudiante(Estudiante estudiante) {
        return sugerenciaRepository.findAll().stream()
            .filter(sugerencia -> sugerencia.getEstudiante().equals(estudiante))
            .toList();
    }
}