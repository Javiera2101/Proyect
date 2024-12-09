package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicacionesController {

    @GetMapping("/publicaciones")
    public String mostrarSeleccionPublicaciones() {
        return "publicaciones"; // El nombre del archivo HTML sin la extensión
    }
}