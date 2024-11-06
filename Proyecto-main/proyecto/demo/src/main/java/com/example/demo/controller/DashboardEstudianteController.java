package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Estudiante;
import com.example.demo.service.EstudianteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardEstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("/dashboard-estudiante")
    public String showDashboard(HttpSession session, Model model) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        
        if (tipoUsuario == null || !tipoUsuario.equals("estudiante")) {
            return "redirect:/login";
        }

        Estudiante estudiante = estudianteService.buscarPorCorreo(correoUsuario);
        if (estudiante == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("nombreEstudiante", estudiante.getNombreEstudiante());
        model.addAttribute("carreraEstudiante", estudiante.getCarreraEstudiante());
        // Puedes agregar más atributos según necesites
        
        return "dashboard-estudiante";
    }
}