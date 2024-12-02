package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PublicacionAcademico;

public interface PublicacionAcademicoRepository extends JpaRepository<PublicacionAcademico, Integer> {
}