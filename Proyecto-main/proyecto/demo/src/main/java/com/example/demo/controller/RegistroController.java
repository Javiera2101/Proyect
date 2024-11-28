package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.EmailValidationService;
import com.example.demo.service.EstudianteService;
import com.example.demo.service.PoloService;

@Controller
public class RegistroController {

    @Autowired
    private AcademicoService academicoService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private PoloService poloService;

    @Autowired
    private EmailValidationService emailValidationService;


    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> datos) {
        String tipoUsuario = datos.get("tipoUsuario");
        String correo = datos.get("correo");
        
        try {
            // Validación de correo con ZeroBounce
            if (!emailValidationService.isEmailValid(correo)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Correo electrónico inválido"));
            }

            // Validación de correo único según el tipo de usuario
            switch (tipoUsuario) {
                case "academico" -> {
                    if (academicoService.existePorCorreo(correo)) {
                        return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está registrado para un académico"));
                    }
                    Academico academico = new Academico();
                    academico.setNomAcademico(datos.get("nombre"));
                    academico.setCorreoUbb(correo);
                    academico.setContrasenaAcademico(datos.get("contrasena"));
                    academico.setDepartamento(datos.get("departamento"));
                    academicoService.registrarAcademico(academico);
                }
                case "estudiante" -> {
                    if (estudianteService.existePorCorreo(correo)) {
                        return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está registrado para un estudiante"));
                    }
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombreEstudiante(datos.get("nombre"));
                    estudiante.setCorreoEstudiante(correo);
                    estudiante.setContrasenaEstudiante(datos.get("contrasena"));
                    estudiante.setCarreraEstudiante(datos.get("carrera"));
                    estudianteService.registrarEstudiante(estudiante);
                }
                case "polo" -> {
                    if (poloService.existePorCorreo(correo)) {
                        return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está registrado para un polo"));
                    }
                    Polo polo = new Polo();
                    polo.setNombrePolo(datos.get("nombre"));
                    polo.setCorreoPolo(correo);
                    polo.setContrasenaPolo(datos.get("contrasena"));
                    polo.setNumTelefono(Integer.parseInt(datos.get("numTelefono")));
                    poloService.registrarPolo(polo);
                }
                default -> {
                    return ResponseEntity.badRequest().body(Map.of("error", "Tipo de usuario no válido"));
                }
            }
            return ResponseEntity.ok("Registro exitoso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error en el registro: " + e.getMessage()));
        }
    }
}