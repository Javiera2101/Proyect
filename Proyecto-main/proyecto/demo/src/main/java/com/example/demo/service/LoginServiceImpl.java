package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.repository.AcademicoRepository;
import com.example.demo.repository.EstudianteRepository;
import com.example.demo.repository.PoloRepository;
import com.example.demo.security.PasswordHasher;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AcademicoRepository academicoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PoloRepository poloRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    @Override
    public String autenticarUsuario(String correo, String contrasena) throws Exception {
        // Buscar en académicos
        Academico academico = academicoRepository.findByCorreoUbb(correo);
        if (academico != null) {
            // Verificar si la contraseña coincide
            if (passwordHasher.verifyPassword(academico.getContrasenaAcademico(), contrasena)) {
                return "academico";
            }
        }

        // Hacer lo mismo para estudiantes y polos
        Estudiante estudiante = estudianteRepository.findByCorreoEstudiante(correo);
        if (estudiante != null) {
            if (passwordHasher.verifyPassword(estudiante.getContrasenaEstudiante(), contrasena)) {
                return "estudiante";
            }
        }

        Polo polo = poloRepository.findByCorreoPolo(correo);
        if (polo != null) {
            if (passwordHasher.verifyPassword(polo.getContrasenaPolo(), contrasena)) {
                return "polo";
            }
        }

        throw new Exception("Credenciales inválidas");
    }

    @Override
    public String obtenerNombreUsuario(String correo) {
        // Buscar en académicos
        Academico academico = academicoRepository.findByCorreoUbb(correo);
        if (academico != null) {
            return academico.getNomAcademico(); // Retorna el nombre del académico
        }

        // Buscar en estudiantes
        Estudiante estudiante = estudianteRepository.findByCorreoEstudiante(correo);
        if (estudiante != null) {
            return estudiante.getNombreEstudiante(); // Retorna el nombre del estudiante
        }

        // Buscar en polos
        Polo polo = poloRepository.findByCorreoPolo(correo);
        if (polo != null) {
            return polo.getNombrePolo(); // Retorna el nombre del polo
        }

        return null; // Si no se encuentra el usuario, retorna null
    }
}
