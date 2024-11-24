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

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.service.PoloService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/polo")
public class PoloController {
    
    @Autowired
    PoloService poloService;

    @GetMapping("")
    public List<Polo> list() {
        return poloService.buscarPolo();
    }

    @PostMapping("/registroPolo")
    public ResponseEntity<Polo> registrarPolo(@RequestBody Polo polo) {
        // Validación para comprobar que el nombre no sea nulo
        if (polo.getNombrePolo() == null || polo.getNombrePolo().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Retorna 400 BAD_REQUEST si el nombre es nulo o vacío
        }
        
        Polo nuevoPolo = poloService.registrarPolo(polo);
        return new ResponseEntity<>(nuevoPolo, HttpStatus.CREATED);
    }

    @GetMapping("/dashboard-polo/identificar-academicos")
    public String identificarAcademicos(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        Polo polo = poloService.buscarPorCorreo(correoUsuario); // Verifica que el polo esté autenticado

        if (polo == null) {
            return "redirect:/login"; // Redirigir si no está autenticado
        }

        List<Academico> academicos = poloService.obtenerAcademicos();

        model.addAttribute("academicos", academicos);

        return "identificar-academicos"; // Vista que mostraría la lista
    }

    @GetMapping("/dashboard-polo/identificar-estudiantes")
    public String identificarEstudiantes(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        Polo polo = poloService.buscarPorCorreo(correoUsuario); // Verifica que el polo esté autenticado

        if (polo == null) {
            return "redirect:/login"; // Redirigir si no está autenticado
        }

        List<Estudiante> estudiantes = poloService.obtenerEstudiantes();

        model.addAttribute("estudiantes", estudiantes);

        return "identificar-estudiantes"; // Vista que mostraría la lista
    }
}
