package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.demo.model.Proyectos;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PoloService;
import com.example.demo.service.ProyectosService;

import jakarta.servlet.http.HttpSession;

public class ProyectosControllerTest {

    @InjectMocks
    private ProyectosController proyectosController;

    @Mock
    private ProyectosService proyectosService;

    @Mock
    private PoloService poloService;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    private Polo poloPrueba;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configurar un polo de prueba
        poloPrueba = new Polo();
        poloPrueba.setIdPolo(1);
        poloPrueba.setNombrePolo("Polo Test");
        poloPrueba.setCorreoPolo("polo@ubb.cl");
    }

    @Test
    public void testMostrarProyectosPublicos() {
        // Preparar datos de prueba
        Proyectos proyecto1 = new Proyectos();
        proyecto1.setNombreProyecto("Proyecto 1");
        
        Proyectos proyecto2 = new Proyectos();
        proyecto2.setNombreProyecto("Proyecto 2");

        // Simular servicio
        when(proyectosService.obtenerTodosLosProyectos())
            .thenReturn(List.of(proyecto1, proyecto2));

        // Ejecutar método
        String vista = proyectosController.mostrarProyectosPublicos(model);

        // Verificar resultados
        assertEquals("proyectos-polo", vista);
        verify(model).addAttribute("proyectos", List.of(proyecto1, proyecto2));
    }

    @Test
    public void testCrearProyecto_Success() throws Exception {
        // Configurar sesión
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");
        when(poloService.buscarPorCorreo("polo@ubb.cl")).thenReturn(poloPrueba);

        // Simular archivo de imagen
        MockMultipartFile imagen = new MockMultipartFile(
            "imagen", 
            "test.jpg", 
            "image/jpeg", 
            "contenido de imagen".getBytes()
        );

        // Simular resultado de Cloudinary
        Map<String, String> cloudinaryResult = new HashMap<>();
        cloudinaryResult.put("url", "http://imagen-url.com");
        when(cloudinaryService.uploadFile(any())).thenReturn(cloudinaryResult);

        // Ejecutar método
        ResponseEntity<?> response = proyectosController.crearProyecto(
            "Proyecto Test", 
            "Descripción del proyecto", 
            "Investigación", 
            "En Progreso", 
            "2023-01-01", 
            "2023-12-31", 
            imagen, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(proyectosService).crearProyecto(any(Proyectos.class), any(Polo.class));
    }

    @Test
    public void testCrearProyecto_FechasInvalidas() throws Exception {
        // Configurar sesión
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");
        when(poloService.buscarPorCorreo("polo@ubb.cl")).thenReturn(poloPrueba);

        // Ejecutar método con fechas inválidas
        ResponseEntity<?> response = proyectosController.crearProyecto(
            "Proyecto Test", 
            "Descripción del proyecto", 
            "Investigación", 
            "En Progreso", 
            "2023-12-31", 
            "2023-01-01", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testMostrarFormularioProyecto_Autorizado() {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");

        // Ejecutar método
        String vista = proyectosController.mostrarFormularioProyecto(session);

        // Verificar resultados
        assertEquals("crear-proyecto", vista);
    }

    @Test
    public void testMostrarFormularioProyecto_NoAutorizado() {
        // Configurar sesión sin tipo de usuario
        when(session.getAttribute("tipoUsuario")).thenReturn(null);

        // Ejecutar método
        String vista = proyectosController.mostrarFormularioProyecto(session);

        // Verificar resultados
        assertEquals("redirect:/login", vista);
    }
}