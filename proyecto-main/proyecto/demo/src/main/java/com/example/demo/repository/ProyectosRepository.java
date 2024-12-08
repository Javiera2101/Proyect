package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Polo;
import com.example.demo.model.Proyectos;

public interface ProyectosRepository extends JpaRepository<Proyectos, Integer> {
    // Método para buscar proyectos por polo si lo necesitas
    List<Proyectos> findByPolo(Polo polo);
}