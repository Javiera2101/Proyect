package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Polo;
import com.example.demo.model.PublicacionPolo;
import com.example.demo.service.PoloService;
import com.example.demo.service.PublicacionPoloService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/polo/publicaciones")
public class PublicacionPoloController {

    @Autowired
    private PublicacionPoloService publicacionService;

    @Autowired
    private PoloService poloService;

    @GetMapping("/crear")
    public String mostrarFormularioPublicacion(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        if (tipoUsuario == null || !tipoUsuario.equals("polo")) {
            return "redirect:/login";
        }

        return "publicacion-polo";
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearPublicacion(@RequestBody Map<String, String> datos, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Polo polo = poloService.buscarPorCorreo(correoUsuario);
            PublicacionPolo publicacion = new PublicacionPolo();
            publicacion.setNomNoticiaPolo(datos.get("nombrePublicacion"));
            publicacion.setDescripcionNoticiaPolo(datos.get("descripcionPublicacion"));
            publicacion.setCategoriaNoticiaPolo(datos.get("categoriaPublicacion"));
            publicacion.setFechaPublicacionNoticiaPolo(java.time.LocalDateTime.now());

            publicacionService.crearPublicacion(publicacion, polo);

            return ResponseEntity.ok("Publicación creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la publicación: " + e.getMessage());
        }
    }
}
