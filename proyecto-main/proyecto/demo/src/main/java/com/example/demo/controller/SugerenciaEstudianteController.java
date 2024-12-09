package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Estudiante;
import com.example.demo.model.SugerenciaEstudiante;
import com.example.demo.service.EstudianteService;
import com.example.demo.service.SugerenciaEstudianteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/estudiante/sugerencias")
public class SugerenciaEstudianteController {

    @Autowired
    private SugerenciaEstudianteService sugerenciaService;

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("/crear")
    public String mostrarFormularioSugerencia(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        // Verificar si el usuario está logueado y es estudiante
        if (tipoUsuario == null || !tipoUsuario.equals("estudiante")) {
            return "redirect:/login"; // Redirigir al login si no está logueado o no es estudiante
        }

        return "sugerencia-estudiante"; // Vista para crear sugerencia
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearSugerencia(@RequestBody Map<String, String> datos, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Estudiante estudiante = estudianteService.buscarPorCorreo(correoUsuario);
            SugerenciaEstudiante sugerencia = new SugerenciaEstudiante();
            sugerencia.setNombreSugerenciaEstudiante(datos.get("nombreSugerencia"));
            sugerencia.setDescripcionSugerenciaEstudiante(datos.get("descripcionSugerencia"));

            sugerenciaService.crearSugerencia(sugerencia, estudiante);

            return ResponseEntity.ok("Sugerencia creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la sugerencia: " + e.getMessage());
        }
    }

    @GetMapping("/mis-sugerencias")
    public String mostrarMisSugerencias(HttpSession session, Model model) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        
        // Verificar si el usuario está logueado y es estudiante
        if (tipoUsuario == null || !tipoUsuario.equals("estudiante")) {
            return "redirect:/login";
        }

        // Buscar al estudiante por correo
        Estudiante estudiante = estudianteService.buscarPorCorreo(correoUsuario);
        
        if (estudiante == null) {
            return "redirect:/login";
        }

        // Obtener las sugerencias del estudiante
        List<SugerenciaEstudiante> sugerenciasEstudiante = sugerenciaService.obtenerSugerenciasPorEstudiante(estudiante);
        
        // Agregar las sugerencias al modelo
        model.addAttribute("sugerenciasEstudiante", sugerenciasEstudiante);
        
        return "mis-sugerencias-estudiante";
    }
}