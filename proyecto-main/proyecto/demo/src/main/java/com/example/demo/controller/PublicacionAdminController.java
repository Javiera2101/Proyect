package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.PublicacionAcademico;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PublicacionAcademicoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/polo/publicaciones-academicas")
public class PublicacionAdminController {

    @Autowired
    private PublicacionAcademicoService publicacionService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Método para mostrar publicaciones con opción de editar/eliminar
    @GetMapping("/administrar")
    public String mostrarPublicacionesParaAdministrar(Model model, HttpSession session) {
        // Verificar que solo el polo puede acceder
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (!"polo".equals(tipoUsuario)) {
            return "redirect:/login";
        }

        List<PublicacionAcademico> publicaciones = publicacionService.obtenerTodasLasPublicaciones();
        model.addAttribute("publicaciones", publicaciones);
        return "administrar-publicaciones-academicas";
    }

    // Método para eliminar publicación
    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarPublicacion(
        @PathVariable int id, 
        HttpSession session
    ) {
        // Verificar permisos
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (!"polo".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No tienes permiso para realizar esta acción");
        }

        try {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.ok("Publicación eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al eliminar la publicación: " + e.getMessage());
        }
    }

    // Método para editar publicación
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(
        @PathVariable int id, 
        Model model, 
        HttpSession session
    ) {
        // Verificar permisos
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (!"polo".equals(tipoUsuario)) {
            return "redirect:/login";
        }

        PublicacionAcademico publicacion = publicacionService.obtenerPublicacionPorId(id);
        model.addAttribute("publicacion", publicacion);
        return "editar-publicacion-academica";
    }

    @PostMapping("/editar")
    @ResponseBody
    public ResponseEntity<?> editarPublicacion(
        @RequestParam("idPublicacion") int idPublicacion,
        @RequestParam("nombrePublicacion") String nombrePublicacion,
        @RequestParam("descripcionPublicacion") String descripcionPublicacion,
        @RequestParam("categoriaPublicacion") String categoriaPublicacion,
        @RequestParam(value = "imagen", required = false) MultipartFile imagen,
        HttpSession session
    ) {
        // Verificar permisos
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if (!"polo".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No tienes permiso para realizar esta acción");
        }

        try {
            PublicacionAcademico publicacion = publicacionService.obtenerPublicacionPorId(idPublicacion);
            
            publicacion.setNomNoticiaAcademico(nombrePublicacion);
            publicacion.setDescripcionNoticiaAcademico(descripcionPublicacion);
            publicacion.setCategoriaNoticiaAcademico(categoriaPublicacion);

            // Manejar la imagen si se proporciona
            if (imagen != null && !imagen.isEmpty()) {
                Map uploadResult = cloudinaryService.uploadFile(imagen);
                publicacion.setImagenUrlAcademico(uploadResult.get("url").toString());
            }

            publicacionService.actualizarPublicacion(publicacion);

            return ResponseEntity.ok("Publicación actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar la publicación: " + e.getMessage());
        }
    }
}