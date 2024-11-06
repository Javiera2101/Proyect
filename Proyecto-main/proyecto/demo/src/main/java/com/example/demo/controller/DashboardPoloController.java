package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Polo;
import com.example.demo.service.PoloService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardPoloController {
    
    @Autowired
    private PoloService poloService;

    @GetMapping("/dashboard-polo")
    public String showDashboard(HttpSession session, Model model) {
        // Verificar si hay una sesión activa y es de tipo polo
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        
        if (tipoUsuario == null || !tipoUsuario.equals("polo")) {
            return "redirect:/login";
        }

        // Obtener datos del polo
        Polo polo = poloService.buscarPorCorreo(correoUsuario);
        if (polo == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("nombrePolo", polo.getNombrePolo());
        
        // Aquí puedes agregar más atributos al modelo si es necesario
        // Por ejemplo:
        // model.addAttribute("cantidadPublicaciones", publicacionesService.contarPublicaciones());
        // model.addAttribute("cantidadEjes", ejesService.contarEjes());

        return "dashboard-polo";
    }
}