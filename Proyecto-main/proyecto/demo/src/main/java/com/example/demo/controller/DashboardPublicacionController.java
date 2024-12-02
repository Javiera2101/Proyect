package com.example.demo.controller;

import com.example.demo.service.PublicacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noticias")
public class DashboardPublicacionController {

    private final PublicacionService publicacionService;

    public DashboardPublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @GetMapping
    public String mostrarNoticias(Model model) {
        model.addAttribute("publicaciones", publicacionService.obtenerPublicaciones());
        return "noticias"; // Nombre del archivo Thymeleaf: "noticias.html"
    }
}



