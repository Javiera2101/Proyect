package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Academico;
import com.example.demo.model.PublicacionAcademico;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PublicacionAcademicoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/academico/publicaciones")
public class PublicacionAcademicoController {

    @Autowired
    private PublicacionAcademicoService publicacionService;

    @Autowired
    private AcademicoService academicoService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/crear")
    public String mostrarFormularioPublicacion(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        // Verificar si el usuario está logueado y es académico
        if (tipoUsuario == null || !tipoUsuario.equals("academico")) {
            return "redirect:/login"; 
        }

        return "publicacion-academico"; 
    }

    @PostMapping("/crear")
    @ResponseBody
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<?> crearPublicacion(
        @RequestParam("nombrePublicacion") String nombrePublicacion,
        @RequestParam("descripcionPublicacion") String descripcionPublicacion,
        @RequestParam("categoriaPublicacion") String categoriaPublicacion,
        @RequestParam(value = "imagen", required = false) MultipartFile imagen,
        HttpSession session
    ) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Academico academico = academicoService.buscarPorCorreo(correoUsuario);
            PublicacionAcademico publicacion = new PublicacionAcademico();
            publicacion.setNomNoticiaAcademico(nombrePublicacion);
            publicacion.setDescripcionNoticiaAcademico(descripcionPublicacion);
            publicacion.setCategoriaNoticiaAcademico(categoriaPublicacion);
            publicacion.setFechaPublicacionNoticiaAcademico(LocalDateTime.now());
            publicacion.setAcademico(academico);

            // Subir imagen si existe
            if (imagen != null && !imagen.isEmpty()) {
                // Validaciones adicionales
                String contentType = imagen.getContentType();
                long fileSize = imagen.getSize();

                // Validar tipo de archivo (solo imágenes)
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest().body("Solo se permiten archivos de imagen");
                }

                // Validar tamaño del archivo (por ejemplo, máximo 5MB)
                if (fileSize > 5 * 1024 * 1024) {
                    return ResponseEntity.badRequest().body("El archivo no debe superar los 5MB");
                }

                @SuppressWarnings("rawtypes")
                Map uploadResult = cloudinaryService.uploadFile(imagen);
                publicacion.setImagenUrlAcademico(uploadResult.get("url").toString());
            }

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