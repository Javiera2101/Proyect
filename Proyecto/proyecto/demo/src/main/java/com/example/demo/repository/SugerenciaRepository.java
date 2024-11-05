package com.example.demo.repository;

import com.example.demo.model.Sugerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SugerenciaRepository extends JpaRepository<Sugerencia, Integer> {
}
