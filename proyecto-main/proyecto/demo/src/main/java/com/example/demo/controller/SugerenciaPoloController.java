package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Polo;
import com.example.demo.model.SugerenciaAcademico;
import com.example.demo.model.SugerenciaEstudiante;
import com.example.demo.service.PoloService;
import com.example.demo.service.SugerenciaAcademicoService;
import com.example.demo.service.SugerenciaEstudianteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/polo/sugerencias")
public class SugerenciaPoloController {

    @Autowired
    private SugerenciaAcademicoService sugerenciaService;

    @Autowired
    private SugerenciaEstudianteService sugerenciaEstudianteService;

    @Autowired
    private PoloService poloService;

    @GetMapping("/revisar")
    public String mostrarSugerencias(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        Polo polo = poloService.buscarPorCorreo(correoUsuario);

        if (polo == null) {
            return "redirect:/login";
        }

        // Cambiar de obtenerSugerenciasPendientes() a obtenerTodasSugerencias()
        List<SugerenciaAcademico> sugerencias = sugerenciaService.obtenerTodasSugerencias();
        model.addAttribute("sugerencias", sugerencias);

        return "sugerencias-pendientes-polo";
    }

    @PostMapping("/actualizar-estado")
    @ResponseBody
    public ResponseEntity<?> actualizarEstadoSugerencia(
        @RequestBody Map<String, String> payload,
        HttpSession session
    ) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        Polo polo = poloService.buscarPorCorreo(correoUsuario);

        if (polo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        try {
            int idSugerencia = Integer.parseInt(payload.get("idSugerencia"));
            String nuevoEstado = payload.get("nuevoEstado");

            SugerenciaAcademico sugerenciaActualizada = 
                sugerenciaService.actualizarEstadoSugerencia(idSugerencia, nuevoEstado);

            return ResponseEntity.ok(sugerenciaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la sugerencia");
        }
    }

    @GetMapping("/sugerencias-estudiantes")
        public String mostrarSugerenciasEstudiantes(Model model, HttpSession session) {
            String correoUsuario = (String) session.getAttribute("correoUsuario");
            Polo polo = poloService.buscarPorCorreo(correoUsuario);

            if (polo == null) {
                return "redirect:/login";
            }

            // Obtener todas las sugerencias de estudiantes
            List<SugerenciaEstudiante> sugerencias = sugerenciaEstudianteService.obtenerTodasSugerencias();
            model.addAttribute("sugerencias", sugerencias);

            return "sugerencias-pendientes-estudiante";
        }

        @PostMapping("/sugerencias-estudiantes/actualizar-estado")
        @ResponseBody
        public ResponseEntity<?> actualizarEstadoSugerenciaEstudiante(
            @RequestBody Map<String, String> payload,
            HttpSession session
        ) {
            String correoUsuario = (String) session.getAttribute("correoUsuario");
            Polo polo = poloService.buscarPorCorreo(correoUsuario);

            if (polo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
            }

            try {
                int idSugerencia = Integer.parseInt(payload.get("idSugerencia"));
                String nuevoEstado = payload.get("nuevoEstado");

                SugerenciaEstudiante sugerenciaActualizada = 
                    sugerenciaEstudianteService.actualizarEstadoSugerencia(idSugerencia, nuevoEstado);

                return ResponseEntity.ok(sugerenciaActualizada);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error al actualizar la sugerencia");
            }
        }
}