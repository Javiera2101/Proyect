package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        
        if (tipoUsuario != null && nombreUsuario != null) {
            model.addAttribute("nombreUsuario", nombreUsuario);
            return "index-logged";
        } else {
            return "index";
        }
    }
}