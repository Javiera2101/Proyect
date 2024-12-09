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

import com.example.demo.model.Academico;
import com.example.demo.model.SugerenciaAcademico;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.SugerenciaAcademicoService;

import jakarta.servlet.http.HttpSession;

public class SugerenciaAcademicoControllerTest {

    @InjectMocks
    private SugerenciaAcademicoController controller;

    @Mock
    private SugerenciaAcademicoService sugerenciaService;

    @Mock
    private AcademicoService academicoService;

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
        when(session.getAttribute("tipoUsuario")).thenReturn("academico");
        
        String vista = controller.mostrarFormularioSugerencia(session);
        
        assertEquals("sugerencia-academico", vista);
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

        when(session.getAttribute("correoUsuario")).thenReturn("academico@ubb.cl");
        
        Academico academico = new Academico();
        when(academicoService.buscarPorCorreo("academico@ubb.cl")).thenReturn(academico);

        // Ejecutar método
        ResponseEntity<?> respuesta = controller.crearSugerencia(datos, session);

        // Verificar
        assertEquals(200, respuesta.getStatusCodeValue());
        verify(sugerenciaService).crearSugerencia(any(SugerenciaAcademico.class), eq(academico));
    }

    @Test
    public void testMostrarMisSugerencias_Success() {
        // Preparar datos
        when(session.getAttribute("tipoUsuario")).thenReturn("academico");
        when(session.getAttribute("correoUsuario")).thenReturn("academico@ubb.cl");

        Academico academico = new Academico();
        when(academicoService.buscarPorCorreo("academico@ubb.cl")).thenReturn(academico);

        List<SugerenciaAcademico> sugerencias = List.of(new SugerenciaAcademico());
        when(sugerenciaService.obtenerSugerenciasPorAcademico(academico)).thenReturn(sugerencias);

        // Ejecutar método
        String vista = controller.mostrarMisSugerencias(session, model);

        // Verificar
        assertEquals("mis-sugerencias-academico", vista);
        verify(model).addAttribute(eq("sugerenciasAcademico"), eq(sugerencias));
    }
}
