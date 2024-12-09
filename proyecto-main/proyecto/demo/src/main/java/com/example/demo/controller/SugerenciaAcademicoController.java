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
import com.example.demo.model.SugerenciaAcademico;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.SugerenciaAcademicoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/academico/sugerencias")
public class SugerenciaAcademicoController {

    @Autowired
    private SugerenciaAcademicoService sugerenciaService;

    @Autowired
    private AcademicoService academicoService;

    @GetMapping("/crear")
    public String mostrarFormularioSugerencia(HttpSession session) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        // Verificar si el usuario está logueado y es académico
        if (tipoUsuario == null || !tipoUsuario.equals("academico")) {
            return "redirect:/login"; // Redirigir al login si no está logueado o no es académico
        }

        return "sugerencia-academico"; // Vista para crear sugerencia
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearSugerencia(@RequestBody Map<String, String> datos, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Academico academico = academicoService.buscarPorCorreo(correoUsuario);
            SugerenciaAcademico sugerencia = new SugerenciaAcademico();
            sugerencia.setNombreSugerenciaAcademico(datos.get("nombreSugerencia"));
            sugerencia.setDescripcionSugerenciaAcademico(datos.get("descripcionSugerencia"));

            sugerenciaService.crearSugerencia(sugerencia, academico);

            return ResponseEntity.ok("Sugerencia creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la sugerencia: " + e.getMessage());
        }
    }

    @GetMapping("/mis-sugerencias")
    public String mostrarMisSugerencias(HttpSession session, Model model) {
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String correoUsuario = (String) session.getAttribute("correoUsuario");
        
        // Verificar si el usuario está logueado y es académico
        if (tipoUsuario == null || !tipoUsuario.equals("academico")) {
            return "redirect:/login";
        }

        // Buscar al académico por correo
        Academico academico = academicoService.buscarPorCorreo(correoUsuario);
        
        if (academico == null) {
            return "redirect:/login";
        }

        // Obtener las sugerencias del académico
        List<SugerenciaAcademico> sugerenciasAcademico = sugerenciaService.obtenerSugerenciasPorAcademico(academico);
        
        // Agregar las sugerencias al modelo
        model.addAttribute("sugerenciasAcademico", sugerenciasAcademico);
        
        return "mis-sugerencias-academico";
    }
}