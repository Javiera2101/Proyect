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
public class AcademicoServiceImpl implements AcademicoService{

    @Autowired 
    AcademicoRepository repAcademico;

    @Autowired
    EstudianteRepository estudianteRepository;

    @Override
    public List<Academico> buscarTodosLosAcademicos() {
        return repAcademico.findAll();
    }

    @Override
    public Academico buscarAcademicoPorId(int id) {
        return repAcademico.findById(id).get();
    }

    @Override
    public void Guardar(Academico academico) {
        repAcademico.save(academico);
    }

    @Override
    public void BorrarAcademicoPorId(int id) {
        repAcademico.deleteById(id);
    }
    
    @Override
    public Academico registrarAcademico(Academico academico) {
        return repAcademico.save(academico);
    }

    @Override
    public Academico buscarPorCorreo(String correo) {
        return repAcademico.findByCorreoUbb(correo);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return repAcademico.existsByCorreoUbb(correo);
    }

    @Override
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteRepository.findAll();
    }
}
