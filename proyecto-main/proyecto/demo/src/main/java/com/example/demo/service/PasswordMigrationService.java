package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.repository.AcademicoRepository;
import com.example.demo.repository.EstudianteRepository;
import com.example.demo.repository.PoloRepository;
import com.example.demo.security.PasswordHasher;

import jakarta.transaction.Transactional;

@Service
public class PasswordMigrationService {
    @Autowired
    private AcademicoRepository academicoRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private PoloRepository poloRepository;
    
    @Autowired
    private PasswordHasher passwordHasher;

    @Transactional
    public void migrarContrasenas() {
        // Migrar contraseñas de Académicos
        migrarContrasenaAcademicos();
        
        // Migrar contraseñas de Estudiantes
        migrarContrasenaEstudiantes();
        
        // Migrar contraseñas de Polo
        migrarContrasenaPolo();
    }

    private void migrarContrasenaAcademicos() {
        List<Academico> academicos = academicoRepository.findAll();
        
        for (Academico academico : academicos) {
            // Solo hashear si no está ya hasheado
            if (!academico.getContrasenaAcademico().startsWith("$argon2")) {
                String hashedPassword = passwordHasher.hashPassword(academico.getContrasenaAcademico());
                academico.setContrasenaAcademico(hashedPassword);
                academicoRepository.save(academico);
            }
        }
        System.out.println("Migración de contraseñas de Académicos completada");
    }

    private void migrarContrasenaEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        
        for (Estudiante estudiante : estudiantes) {
            // Solo hashear si no está ya hasheado
            if (!estudiante.getContrasenaEstudiante().startsWith("$argon2")) {
                String hashedPassword = passwordHasher.hashPassword(estudiante.getContrasenaEstudiante());
                estudiante.setContrasenaEstudiante(hashedPassword);
                estudianteRepository.save(estudiante);
            }
        }
        System.out.println("Migración de contraseñas de Estudiantes completada");
    }

    private void migrarContrasenaPolo() {
        List<Polo> polos = poloRepository.findAll();
        
        for (Polo polo : polos) {
            // Solo hashear si no está ya hasheado
            if (!polo.getContrasenaPolo().startsWith("$argon2")) {
                String hashedPassword = passwordHasher.hashPassword(polo.getContrasenaPolo());
                polo.setContrasenaPolo(hashedPassword);
                poloRepository.save(polo);
            }
        }
        System.out.println("Migración de contraseñas de Polo completada");
    }
}