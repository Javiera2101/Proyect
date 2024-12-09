package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.repository.AcademicoRepository;
import com.example.demo.repository.EstudianteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService{

    @Autowired
    EstudianteRepository repEstudiante;

    @Autowired
    AcademicoRepository academicoRepository;

    @Override
    public List<Estudiante> buscarTodosLosEstudiantes() {
        return repEstudiante.findAll();
    }

    @Override
    public Estudiante buscarEstudiantePorId(int id) {
        return repEstudiante.findById(id).get();
    }

    @Override
    public void Guardar(Estudiante estudiante) {
        repEstudiante.save(estudiante);
    }

    @Override
    public void BorrarEstudiantePorId(int id) {
        repEstudiante.deleteById(id);
    }
    
    @Override
    public Estudiante registrarEstudiante(Estudiante estudiante) {
        return repEstudiante.save(estudiante);
    }

    @Override
    public Estudiante buscarPorCorreo(String correo) {
        return repEstudiante.findByCorreoEstudiante(correo);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return repEstudiante.existsByCorreoEstudiante(correo);      
    }

    @Override
    public List<Academico> obtenerAcademicos() {
        return academicoRepository.findAll();
    }
}
