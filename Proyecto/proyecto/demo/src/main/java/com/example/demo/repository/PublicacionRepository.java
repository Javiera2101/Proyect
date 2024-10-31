package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.publicación;

public interface PublicacionRepository extends JpaRepository <publicación, Integer>{
    
}