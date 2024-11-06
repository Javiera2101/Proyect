package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Academico;
import com.example.demo.service.AcademicoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardAcademicoController {
    
    @Autowired
    private AcademicoService academicoService;

    @GetMapping("/dashboard-academico")
    public String showDashboard(HttpSession session, Model model) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        
        if (tipoUsuario == null || !tipoUsuario.equals("academico")) {
            return "redirect:/login";
        }

        Academico academico = academicoService.buscarPorCorreo(correoUsuario);
        if (academico == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("nombreAcademico", academico.getNomAcademico());
        model.addAttribute("Departamento", academico.getDepartamento());
        // Aquí puedes agregar más atributos al modelo según sea necesario
        
        return "dashboard-academico";
    }
}
