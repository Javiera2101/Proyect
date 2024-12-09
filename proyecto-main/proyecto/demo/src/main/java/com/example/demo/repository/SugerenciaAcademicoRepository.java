package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Academico;
import com.example.demo.model.SugerenciaAcademico;

@Repository
public interface SugerenciaAcademicoRepository extends JpaRepository<SugerenciaAcademico, Integer> {
    List<SugerenciaAcademico> findByAcademico(Academico academico);
    List<SugerenciaAcademico> findByEstadoSugerenciaAcademico(String estado);
}