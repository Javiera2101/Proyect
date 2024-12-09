package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.service.EstudianteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @GetMapping("")
    public List<Estudiante> list() {
        return estudianteService.buscarTodosLosEstudiantes();
    }

    @PostMapping("/registroEstudiante")
    public ResponseEntity<Estudiante> registrarEstudiante(@RequestBody Estudiante estudiante) {
        try {
            Estudiante nuevoEstudiante = estudianteService.registrarEstudiante(estudiante);
            return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/identificar-academicos")
    public String identificarAcademicos(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        Estudiante estudiante = estudianteService.buscarPorCorreo(correoUsuario);

        if (estudiante == null) {
            return "redirect:/login"; // Redirigir si no está autenticado
        }

        List<Academico> academicos = estudianteService.obtenerAcademicos(); // Crear este método en el servicio

        model.addAttribute("academicos", academicos);

        return "identificar-academicos-estudiante"; // Nueva vista
    }
}
