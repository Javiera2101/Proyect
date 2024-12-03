package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Academico;
import com.example.demo.model.PublicacionAcademico;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.PublicacionAcademicoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/academico/publicaciones")
public class PublicacionAcademicoController {

    @Autowired
    private PublicacionAcademicoService publicacionService;

    @Autowired
    private AcademicoService academicoService;

    @GetMapping("/crear")
    public String mostrarFormularioPublicacion(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        if (tipoUsuario == null || !tipoUsuario.equals("academico")) {
            return "redirect:/login";
        }

        return "publicacion-academico";
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearPublicacion(@RequestBody Map<String, String> datos, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Academico academico = academicoService.buscarPorCorreo(correoUsuario);
            PublicacionAcademico publicacion = new PublicacionAcademico();
            publicacion.setNomNoticiaAcademico(datos.get("nombrePublicacion"));
            publicacion.setDescripcionNoticiaAcademico(datos.get("descripcionPublicacion"));
            publicacion.setCategoriaNoticiaAcademico(datos.get("categoriaPublicacion"));

            publicacionService.crearPublicacion(publicacion, academico);

            return ResponseEntity.ok("Publicación creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la publicación: " + e.getMessage());
        }
    }

    @GetMapping("/publicaciones-academicas")
    public String mostrarPublicacionesPublicas(Model model) {
        // Obtener todas las publicaciones
        List<PublicacionAcademico> publicaciones = publicacionService.obtenerTodasLasPublicaciones();
        model.addAttribute("publicaciones", publicaciones);
        return "publicaciones-academicas";
    }
}