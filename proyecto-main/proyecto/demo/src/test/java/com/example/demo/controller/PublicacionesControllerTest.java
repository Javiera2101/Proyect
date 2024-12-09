package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PublicacionesControllerTest {

    @SuppressWarnings("unused")
    private MockMvc mockMvc;
    private PublicacionesController publicacionesController;

    @BeforeEach
    public void setUp() {
        publicacionesController = new PublicacionesController();
        mockMvc = MockMvcBuilders.standaloneSetup(publicacionesController).build();
    }


    @Test
    public void testMostrarSeleccionPublicaciones_ReturnsCorrectView() {
        // Llamar directamente al método del controlador
        String vista = publicacionesController.mostrarSeleccionPublicaciones();
        
        // Verificar que devuelve la vista correcta
        assertEquals("publicaciones", vista);
    }

    @Test
    public void testControllerAnnotation() {
        // Verificar que la clase tiene la anotación @Controller
        assertEquals(true, 
            publicacionesController.getClass().isAnnotationPresent(org.springframework.stereotype.Controller.class),
            "La clase debe estar anotada con @Controller"
        );
    }

    @Test
    public void testNoExceptionThrown() {
        // Verificar que no se lancen excepciones al llamar al método
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            publicacionesController.mostrarSeleccionPublicaciones();
        });
    }
}