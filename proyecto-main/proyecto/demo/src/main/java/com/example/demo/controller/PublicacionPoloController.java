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

import com.example.demo.model.Polo;
import com.example.demo.model.PublicacionPolo;
import com.example.demo.service.CloudinaryService;
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

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/publicaciones-polo")
    public String mostrarPublicacionesPolo(Model model) {
        List<PublicacionPolo> publicaciones = publicacionService.obtenerTodasLasPublicaciones();
        model.addAttribute("publicaciones", publicaciones);
        return "publicaciones-polo";
    }

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
            Polo polo = poloService.buscarPorCorreo(correoUsuario);
            PublicacionPolo publicacion = new PublicacionPolo();
            publicacion.setNomNoticiaPolo(nombrePublicacion);
            publicacion.setDescripcionNoticiaPolo(descripcionPublicacion);
            publicacion.setCategoriaNoticiaPolo(categoriaPublicacion);
            publicacion.setFechaPublicacionNoticiaPolo(LocalDateTime.now());

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
                publicacion.setImagenUrlPolo(uploadResult.get("url").toString());
            }

            publicacionService.crearPublicacion(publicacion, polo);

            return ResponseEntity.ok("Publicación creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la publicación: " + e.getMessage());
        }
    }
}
