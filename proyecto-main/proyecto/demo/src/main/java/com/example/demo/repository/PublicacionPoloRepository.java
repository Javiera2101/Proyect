package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Polo;
import com.example.demo.model.PublicacionPolo;

public interface PublicacionPoloRepository extends JpaRepository<PublicacionPolo, Integer> {
    List<PublicacionPolo> findByPolo(Polo polo);
}