package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.example.demo.model.Polo;
import com.example.demo.model.SugerenciaAcademico;
import com.example.demo.model.SugerenciaEstudiante;
import com.example.demo.service.PoloService;
import com.example.demo.service.SugerenciaAcademicoService;
import com.example.demo.service.SugerenciaEstudianteService;

import jakarta.servlet.http.HttpSession;

public class SugerenciaPoloControllerTest {

    @InjectMocks
    private SugerenciaPoloController controller;

    @Mock
    private SugerenciaAcademicoService sugerenciaAcademicoService;

    @Mock
    private SugerenciaEstudianteService sugerenciaEstudianteService;

    @Mock
    private PoloService poloService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMostrarSugerenciasEstudiantes_Autorizado() {
        // Preparar datos de prueba
        String correoUsuario = "polo@ubb.cl";
        Polo polo = new Polo();
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        when(poloService.buscarPorCorreo(correoUsuario)).thenReturn(polo);

        // Simular sugerencias
        List<SugerenciaEstudiante> sugerencias = List.of(new SugerenciaEstudiante());
        when(sugerenciaEstudianteService.obtenerTodasSugerencias()).thenReturn(sugerencias);

        // Ejecutar método
        String vista = controller.mostrarSugerenciasEstudiantes(model, session);

        // Verificar resultados
        assertEquals("sugerencias-pendientes-estudiante", vista);
        verify(model).addAttribute("sugerencias", sugerencias);
    }

    @Test
    public void testMostrarSugerenciasEstudiantes_NoAutorizado() {
        // Configurar sesión sin usuario
        when(session.getAttribute("correoUsuario")).thenReturn(null);

        // Ejecutar método
        String vista = controller.mostrarSugerenciasEstudiantes(model, session);

        // Verificar resultados
        assertEquals("redirect:/login", vista);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testActualizarEstadoSugerencia_Success() {
        // Preparar datos de prueba
        Map<String, String> payload = new HashMap<>();
        payload.put("idSugerencia", "1");
        payload.put("nuevoEstado", "APROBADO");

        Polo polo = new Polo();
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");
        when(poloService.buscarPorCorreo("polo@ubb.cl")).thenReturn(polo);

        // Simular actualización de sugerencia
        SugerenciaAcademico sugerencia = new SugerenciaAcademico();
        when(sugerenciaAcademicoService.actualizarEstadoSugerencia(1, "APROBADO")).thenReturn(sugerencia);

        // Ejecutar método
        ResponseEntity<?> response = controller.actualizarEstadoSugerencia(payload, session);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        verify(sugerenciaAcademicoService).actualizarEstadoSugerencia(1, "APROBADO");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testActualizarEstadoSugerencia_NoAutorizado() {
        // Configurar sesión sin usuario
        when(session.getAttribute("correoUsuario")).thenReturn(null);

        // Preparar datos de prueba
        Map<String, String> payload = new HashMap<>();
        payload.put("idSugerencia", "1");
        payload.put("nuevoEstado", "APROBADO");

        // Ejecutar método
        ResponseEntity<?> response = controller.actualizarEstadoSugerencia(payload, session);

        // Verificar resultados
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("No autorizado", response.getBody());
    }
}