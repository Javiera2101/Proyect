package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;

public interface AcademicoService {
    
    public List<Academico> buscarTodosLosAcademicos();
    
    public Academico buscarAcademicoPorId(int id);
    
    public void Guardar(Academico academico);
    
    public void BorrarAcademicoPorId(int id);

    public Academico registrarAcademico(Academico academico);

    Academico buscarPorCorreo(String correo);

    boolean existePorCorreo(String correo);

    List<Estudiante> obtenerEstudiantes();
}
