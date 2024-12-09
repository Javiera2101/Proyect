package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.example.demo.model.Estudiante;
import com.example.demo.model.SugerenciaEstudiante;
import com.example.demo.service.EstudianteService;
import com.example.demo.service.SugerenciaEstudianteService;

import jakarta.servlet.http.HttpSession;

public class SugerenciaEstudianteControllerTest {

    @InjectMocks
    private SugerenciaEstudianteController controller;

    @Mock
    private SugerenciaEstudianteService sugerenciaService;

    @Mock
    private EstudianteService estudianteService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMostrarFormularioSugerencia_Autorizado() {
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");
        
        String vista = controller.mostrarFormularioSugerencia(session);
        
        assertEquals("sugerencia-estudiante", vista);
    }

    @Test
    public void testMostrarFormularioSugerencia_NoAutorizado() {
        when(session.getAttribute("tipoUsuario")).thenReturn(null);
        
        String vista = controller.mostrarFormularioSugerencia(session);
        
        assertEquals("redirect:/login", vista);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCrearSugerencia_Success() {
        // Preparar datos
        Map<String, String> datos = new HashMap<>();
        datos.put("nombreSugerencia", "Prueba");
        datos.put("descripcionSugerencia", "Descripción de prueba");

        when(session.getAttribute("correoUsuario")).thenReturn("estudiante@ubb.cl");
        
        Estudiante estudiante = new Estudiante();
        when(estudianteService.buscarPorCorreo("estudiante@ubb.cl")).thenReturn(estudiante);

        // Ejecutar método
        ResponseEntity<?> respuesta = controller.crearSugerencia(datos, session);

        // Verificar
        assertEquals(200, respuesta.getStatusCodeValue());
        verify(sugerenciaService).crearSugerencia(any(SugerenciaEstudiante.class), eq(estudiante));
    }

    @Test
    public void testMostrarMisSugerencias_Success() {
        // Preparar datos
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");
        when(session.getAttribute("correoUsuario")).thenReturn("estudiante@ubb.cl");

        Estudiante estudiante = new Estudiante();
        when(estudianteService.buscarPorCorreo("estudiante@ubb.cl")).thenReturn(estudiante);

        List<SugerenciaEstudiante> sugerencias = List.of(new SugerenciaEstudiante());
        when(sugerenciaService.obtenerSugerenciasPorEstudiante (estudiante)).thenReturn(sugerencias);

        // Ejecutar método
        String vista = controller.mostrarMisSugerencias(session, model);

        // Verificar
        assertEquals("mis-sugerencias-estudiante", vista);
        verify(model).addAttribute(eq("sugerenciasEstudiante"), eq(sugerencias));
    }
}