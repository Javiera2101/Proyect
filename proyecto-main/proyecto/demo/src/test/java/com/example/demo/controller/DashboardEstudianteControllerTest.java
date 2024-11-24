package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.example.demo.model.Estudiante;
import com.example.demo.service.EstudianteService;

import jakarta.servlet.http.HttpSession;

public class DashboardEstudianteControllerTest {

    @InjectMocks
    private DashboardEstudianteController dashboardEstudianteController;

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
    public void testShowDashboard_Success() {
        // Datos de prueba
        String correoUsuario = "test@ubb.cl";
        String tipoUsuario = "estudiante";
        Estudiante estudiante = new Estudiante();
        estudiante.setNombreEstudiante("Test Estudiante");
        estudiante.setCarreraEstudiante("Ingeniería");

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        
        // Simular el comportamiento del servicio
        when(estudianteService.buscarPorCorreo(correoUsuario)).thenReturn(estudiante);

        // Llamar al método showDashboard
        String viewName = dashboardEstudianteController.showDashboard(session, model);

        // Verificar que se establecieron los atributos en el modelo
        verify(model).addAttribute("nombreEstudiante", "Test Estudiante");
        verify(model).addAttribute("carreraEstudiante", "Ingeniería");

        // Verificar que la vista devuelta sea la esperada
        assertEquals("dashboard-estudiante", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_NoTipoUsuario() {
        // Simular el comportamiento de la sesión sin tipo de usuario
        when(session.getAttribute("tipoUsuario")).thenReturn(null);

        // Llamar al método showDashboard
        String viewName = dashboardEstudianteController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_InvalidTipoUsuario() {
        // Simular el comportamiento de la sesión con un tipo de usuario inválido
        when(session.getAttribute("tipoUsuario")).thenReturn("academico");

        // Llamar al método showDashboard
        String viewName = dashboardEstudianteController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_EstudianteNoEncontrado() {
        // Datos de prueba
        String correoUsuario = "test@ubb.cl";
        String tipoUsuario = "estudiante";

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        
        // Simular que no se encuentra el estudiante
        when(estudianteService.buscarPorCorreo(correoUsuario)).thenReturn(null);

        // Llamar al método showDashboard
        String viewName = dashboardEstudianteController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }
}