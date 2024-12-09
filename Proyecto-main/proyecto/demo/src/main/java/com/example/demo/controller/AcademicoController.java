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
import com.example.demo.service.AcademicoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/academico")
public class AcademicoController {
    @Autowired
    AcademicoService academicoService;

    @GetMapping("")
    public List<Academico> list() {
        return academicoService.buscarTodosLosAcademicos();
    }

    @PostMapping("/registroAcademico")
    public ResponseEntity<Academico> registrarAcademico(@RequestBody Academico academico) {
        try {
            Academico nuevoAcademico = academicoService.registrarAcademico(academico);
            return new ResponseEntity<>(nuevoAcademico, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/identificar-estudiantes")
    public String identificarEstudiantes(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        Academico academico = academicoService.buscarPorCorreo(correoUsuario);

        if (academico == null) {
            return "redirect:/login"; // Redirigir si no está autenticado
        }

        List<Estudiante> estudiantes = academicoService.obtenerEstudiantes(); 

        model.addAttribute("estudiantes", estudiantes);

        return "identificar-estudiantes-academico"; 
    }
}
