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

import com.example.demo.model.Polo;
import com.example.demo.service.PoloService;

import jakarta.servlet.http.HttpSession;

public class DashboardPoloControllerTest {

    @InjectMocks
    private DashboardPoloController dashboardPoloController;

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
    public void testShowDashboard_Success() {
        // Datos de prueba
        String correoUsuario = "test@ubb.cl";
        String tipoUsuario = "polo";
        Polo polo = new Polo();
        polo.setNombrePolo("Test Polo");

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        
        // Simular el comportamiento del servicio
        when(poloService.buscarPorCorreo(correoUsuario)).thenReturn(polo);

        // Llamar al método showDashboard
        String viewName = dashboardPoloController.showDashboard(session, model);

        // Verificar que se establecieron los atributos en el modelo
        verify(model).addAttribute("nombrePolo", "Test Polo");

        // Verificar que la vista devuelta sea la esperada
        assertEquals("dashboard-polo", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_NoTipoUsuario() {
        // Simular el comportamiento de la sesión sin tipo de usuario
        when(session.getAttribute("tipoUsuario")).thenReturn(null);

        // Llamar al método showDashboard
        String viewName = dashboardPoloController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_InvalidTipoUsuario() {
        // Simular el comportamiento de la sesión con un tipo de usuario inválido
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Llamar al método showDashboard
        String viewName = dashboardPoloController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testShowDashboard_RedirectToLogin_PoloNoEncontrado() {
        // Datos de prueba
        String correoUsuario = "test@ubb.cl";
        String tipoUsuario = "polo";

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("correoUsuario")).thenReturn(correoUsuario);
        
        // Simular que no se encuentra el polo
        when(poloService.buscarPorCorreo(correoUsuario)).thenReturn(null);

        // Llamar al método showDashboard
        String viewName = dashboardPoloController.showDashboard(session, model);

        // Verificar que se redirija al login
        assertEquals("redirect:/login", viewName);
    }
}