package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Estudiante;
import com.example.demo.model.SugerenciaEstudiante;

@Repository
public interface SugerenciaEstudianteRepository extends JpaRepository<SugerenciaEstudiante, Integer> {
List<SugerenciaEstudiante> findByEstudiante(Estudiante estudiante);
}