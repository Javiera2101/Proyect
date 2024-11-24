package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome_UserLoggedIn() {
        // Datos de prueba
        String tipoUsuario = "estudiante";
        String nombreUsuario = "Test User";

        // Simular el comportamiento de la sesión
        when(session.getAttribute("tipoUsuario")).thenReturn(tipoUsuario);
        when(session.getAttribute("nombreUsuario")).thenReturn(nombreUsuario);

        // Llamar al método home
        String viewName = homeController.home(session, model);

        // Verificar que se estableció el atributo en el modelo
        verify(model).addAttribute("nombreUsuario", nombreUsuario);

        // Verificar que la vista devuelta sea la esperada
        assertEquals("index-logged", viewName);
    }

    @Test
    public void testHome_UserNotLoggedIn() {
        // Simular el comportamiento de la sesión sin usuario
        when(session.getAttribute("tipoUsuario")).thenReturn(null);
        when(session.getAttribute("nombreUsuario")).thenReturn(null);

        // Llamar al método home
        String viewName = homeController.home(session, model);

        // Verificar que no se establecieron atributos en el modelo
        verify(model, never()).addAttribute(anyString(), any());

        // Verificar que la vista devuelta sea la esperada
        assertEquals("index", viewName);
    }
}