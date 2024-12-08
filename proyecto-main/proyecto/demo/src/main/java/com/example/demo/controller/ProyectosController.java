package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Polo;
import com.example.demo.model.Proyectos;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PoloService;
import com.example.demo.service.ProyectosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/polo/proyectos")
public class ProyectosController {

    @Autowired
    private ProyectosService proyectosService;

    @Autowired
    private PoloService poloService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/crear")
    public String mostrarFormularioProyecto(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        if (tipoUsuario == null || !tipoUsuario.equals("polo")) {
            return "redirect:/login";
        }

        return "crear-proyecto";
    }

    @PostMapping("/crear")
    @ResponseBody
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<?> crearProyecto(
        @RequestParam("nombreProyecto") String nombreProyecto,
        @RequestParam("descripcionProyecto") String descripcionProyecto,
        @RequestParam("tipoProyecto") String tipoProyecto,
        @RequestParam("estadoProyecto") String estadoProyecto,
        @RequestParam("fechaInicio") String fechaInicio,
        @RequestParam("fechaFin") String fechaFin,
        @RequestParam(value = "imagen", required = false) MultipartFile imagen,
        HttpSession session
    ) {
        // Agregar más logging o validaciones
        System.out.println("Creando proyecto con los siguientes datos:");
        System.out.println("Nombre: " + nombreProyecto);
        System.out.println("Descripción: " + descripcionProyecto);
        System.out.println("Tipo: " + tipoProyecto);
        System.out.println("Estado: " + estadoProyecto);
        System.out.println("Fecha Inicio: " + fechaInicio);
        System.out.println("Fecha Fin: " + fechaFin);

        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Polo polo = poloService.buscarPorCorreo(correoUsuario);
            
            // Validaciones adicionales
            if (polo == null) {
                return ResponseEntity.badRequest().body("Polo no encontrado");
            }

            Proyectos proyecto = new Proyectos();
            proyecto.setNombreProyecto(nombreProyecto);
            proyecto.setDescripcionProyecto(descripcionProyecto);
            proyecto.setTipoProyecto(tipoProyecto);
            proyecto.setEstadoProyecto(estadoProyecto);
            
            // Cambio en el parseo de fechas con manejo de errores
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                proyecto.setFechaInicioProyecto(LocalDate.parse(fechaInicio, formatter).atStartOfDay());
                proyecto.setFechaFinProyecto(LocalDate.parse(fechaFin, formatter).atStartOfDay());
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Formato de fecha inválido");
            }

            // Validación de fechas
            if (proyecto.getFechaFinProyecto().isBefore(proyecto.getFechaInicioProyecto())) {
                return ResponseEntity.badRequest().body("La fecha de fin no puede ser anterior a la fecha de inicio");
            }

            // Subir imagen si existe
            if (imagen != null && !imagen.isEmpty()) {
                // Validaciones adicionales de imagen
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
                proyecto.setImagenUrlProyecto(uploadResult.get("url").toString());
            }

            proyectosService.crearProyecto(proyecto, polo);

            return ResponseEntity.ok("Proyecto creado exitosamente");
        } catch (Exception e) {
            // Loguear el error completo
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al crear el proyecto: " + e.getMessage());
        }
    }
}