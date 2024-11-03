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

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    @ResponseBody
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> datos) {
        String tipoUsuario = datos.get("tipoUsuario");
        
        try {
            switch (tipoUsuario) {
                case "academico":
                    Academico academico = new Academico();
                    academico.setNom_academico(datos.get("nombre"));
                    academico.setCorreo_ubb(datos.get("correo"));
                    academico.setContrasena_academico(datos.get("contrasena"));
                    academico.setDepartamento(datos.get("departamento"));
                    academicoService.registrarAcademico(academico);
                    break;
                case "estudiante":
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre_estudiante(datos.get("nombre"));
                    estudiante.setCorreo_estudiante(datos.get("correo"));
                    estudiante.setContrasena_estudiante(datos.get("contrasena"));
                    estudiante.setCarrera_estudiante(datos.get("carrera"));
                    estudianteService.registrarEstudiante(estudiante);
                    break;
                case "polo":
                    Polo polo = new Polo();
                    polo.setNombre_polo(datos.get("nombre"));
                    polo.setCorreo_polo(datos.get("correo"));
                    polo.setContrasena_polo(datos.get("contrasena"));
                    polo.setNum_telefono(Integer.parseInt(datos.get("numTelefono")));
                    poloService.registrarPolo(polo); // Corregido de "po zoService" a "poloService"
                    break;
                default:
                    return ResponseEntity.badRequest().body("Tipo de usuario no válido");
            }
            return ResponseEntity.ok("Registro exitoso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
        }
    }
}