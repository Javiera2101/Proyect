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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import com.example.demo.model.Polo;
import com.example.demo.model.PublicacionPolo;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PoloService;
import com.example.demo.service.PublicacionPoloService;

import jakarta.servlet.http.HttpSession;

public class PublicacionPoloControllerTest {

    @InjectMocks
    private PublicacionPoloController publicacionPoloController;

    @Mock
    private PublicacionPoloService publicacionService;

    @Mock
    private PoloService poloService;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMostrarPublicacionesPolo() {
        // Preparar datos de prueba
        List<PublicacionPolo> publicaciones = List.of(new PublicacionPolo());
        when(publicacionService.obtenerTodasLasPublicaciones()).thenReturn(publicaciones);

        // Ejecutar método
        String vista = publicacionPoloController.mostrarPublicacionesPolo(model);

        // Verificar resultados
        assertEquals("publicaciones-polo", vista);
        verify(model).addAttribute("publicaciones", publicaciones);
    }

    @Test
    public void testMostrarFormularioPublicacion_Autorizado() {
        // Configurar sesión autorizada
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");

        // Ejecutar método
        String vista = publicacionPoloController.mostrarFormularioPublicacion(session);

        // Verificar resultados
        assertEquals("publicacion-polo", vista);
    }

    @Test
    public void testMostrarFormularioPublicacion_NoAutorizado() {
        // Configurar sesión no autorizada
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Ejecutar método
        String vista = publicacionPoloController.mostrarFormularioPublicacion(session);

        // Verificar resultados
        assertEquals("redirect:/login", vista);
    }

    @Test
    public void testCrearPublicacion_Success() throws Exception {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");

        // Preparar datos de prueba
        Polo polo = new Polo();
        when(poloService.buscarPorCorreo("polo@ubb.cl")).thenReturn(polo);

        // Simular archivo de imagen
        MockMultipartFile imagen = new MockMultipartFile(
            "imagen", 
            "test.jpg", 
            "image/jpeg", 
            "contenido de imagen".getBytes()
        );

        // Simular resultado de Cloudinary
        Map<String, Object> cloudinaryResult = new HashMap<>();
        cloudinaryResult.put("url", "http://imagen-url.com");
        when(cloudinaryService.uploadFile(any())).thenReturn(cloudinaryResult);

        // Ejecutar método
        ResponseEntity<?> response = publicacionPoloController.crearPublicacion(
            "Título", 
            "Descripción", 
            "Categoría", 
            imagen, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicacionService).crearPublicacion(any(PublicacionPolo.class), eq(polo));
    }

    @Test
    public void testCrearPublicacion_SinAutenticacion() {
        // Configurar sesión sin autenticación
        when(session.getAttribute("correoUsuario")).thenReturn(null);

        // Ejecutar método
        ResponseEntity<?> response = publicacionPoloController.crearPublicacion(
            "Título", 
            "Descripción", 
            "Categoría", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuario no autenticado", response.getBody());
    }

    @Test
    public void testCrearPublicacion_ImagenInvalida() throws Exception {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");

        // Preparar datos de prueba
        Polo polo = new Polo();
        when(poloService.buscarPorCorreo("polo@ubb.cl")).thenReturn(polo);

        // Simular archivo de imagen inválido
        MockMultipartFile imagen = new MockMultipartFile(
            "imagen", 
            "test.txt", 
            "text/plain", 
            "contenido de texto".getBytes()
        );

        // Ejecutar método
        ResponseEntity<?> response = publicacionPoloController.crearPublicacion(
            "Título", 
            "Descripción", 
            "Categoría", 
            imagen, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Solo se permiten archivos de imagen", response.getBody());
    }
}
