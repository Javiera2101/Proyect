package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Polo;
import com.example.demo.model.Proyectos;
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
    public ResponseEntity<?> crearProyecto(
        @RequestParam("nombreProyecto") String nombreProyecto,
        @RequestParam("tipoProyecto") String tipoProyecto,
        @RequestParam("estadoProyecto") String estadoProyecto,
        @RequestParam("fechaInicio") String fechaInicio,
        @RequestParam("fechaFin") String fechaFin,
        HttpSession session
    ) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        try {
            Polo polo = poloService.buscarPorCorreo(correoUsuario);
            Proyectos proyecto = new Proyectos();
            proyecto.setNombreProyecto(nombreProyecto);
            proyecto.setTipoProyecto(tipoProyecto);
            proyecto.setEstadoProyecto(estadoProyecto);
            
            // Cambio en el parseo de fechas
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            proyecto.setFechaInicioProyecto(LocalDate.parse(fechaInicio, formatter).atStartOfDay());
            proyecto.setFechaFinProyecto(LocalDate.parse(fechaFin, formatter).atStartOfDay());

            proyectosService.crearProyecto(proyecto, polo);

            return ResponseEntity.ok("Proyecto creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el proyecto: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public String listarProyectos(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("correoUsuario");

        if (correoUsuario == null) {
            return "redirect:/login";
        }

        Polo polo = poloService.buscarPorCorreo(correoUsuario);
        List<Proyectos> proyectos = proyectosService.obtenerProyectosPorPolo(polo);
        model.addAttribute("proyectos", proyectos);

        return "listar-proyectos";
    }
}