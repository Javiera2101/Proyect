package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;

public interface PoloService {
    
    public List<Polo> buscarPolo();

    public Polo registrarPolo(Polo polo);

    public Polo buscarPorCorreo(String correo);

    public List<Academico> obtenerAcademicos();
    public List<Estudiante> obtenerEstudiantes();
}
