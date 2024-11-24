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

import com.example.demo.model.Academico;
import com.example.demo.service.AcademicoService;

import jakarta.servlet.http.HttpSession;

public class DashboardAcademicoControllerTest {

    @InjectMocks
    private DashboardAcademicoController dashboardAcademicoController;

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
    public void testShowDashboard_Success() {
        // Datos de prueba
        String correoUsuario = "test@ubb.cl";
        String tipoUsuario = "academico";
        Academico academico = new Academico();
        academico.setNomAcademico("Test Academico");
        academico.setDepartamento("Ciencias");

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        
        // Simular el comportamiento del servicio
        when(academicoService.buscarPorCorreo(correoUsuario)).thenReturn(academico);

        // Llamar al método showDashboard
        String viewName = dashboardAcademicoController.showDashboard(session, model);

        // Verificar que se establecieron los atributos en el modelo
        verify(model).addAttribute("nombreAcademico", "Test Academico");
        verify(model).addAttribute("Departamento", "Ciencias");

        // Verificar que la vista devuelta sea la esperada
        assertEquals("dashboard-academico", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_NoTipoUsuario() {
        // Simular el comportamiento de la sesión sin tipo de usuario
        when(session.getAttribute("tipoUsuario")).thenReturn(null);

        // Llamar al método showDashboard
        String viewName = dashboardAcademicoController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_InvalidTipoUsuario() {
        // Simular el comportamiento de la sesión con un tipo de usuario inválido
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Llamar al método showDashboard
        String viewName = dashboardAcademicoController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_AcademicoNoEncontrado() {
        // Datos de prueba
        String correoUsuario = "test@ubb.cl";
        String tipoUsuario = "academico";

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        
        // Simular que no se encuentra el académico
        when(academicoService.buscarPorCorreo(correoUsuario)).thenReturn(null);

        // Llamar al método showDashboard
        String viewName = dashboardAcademicoController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }
}