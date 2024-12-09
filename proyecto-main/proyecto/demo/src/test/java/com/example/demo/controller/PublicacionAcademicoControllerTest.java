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

import com.example.demo.model.Academico;
import com.example.demo.model.PublicacionAcademico;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PublicacionAcademicoService;

import jakarta.servlet.http.HttpSession;

public class PublicacionAcademicoControllerTest {

    @InjectMocks
    private PublicacionAcademicoController publicacionController;

    @Mock
    private PublicacionAcademicoService publicacionService;

    @Mock
    private AcademicoService academicoService;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    private Academico academicoPrueba;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configurar un académico de prueba
        academicoPrueba = new Academico();
        academicoPrueba.setIdAcademico(1);
        academicoPrueba.setNomAcademico("Académico Test");
        academicoPrueba.setCorreoUbb("academico@ubb.cl");
    }

    @Test
    public void testMostrarFormularioPublicacion_Autorizado() {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("academico");

        // Ejecutar método
        String vista = publicacionController.mostrarFormularioPublicacion(session);

        // Verificar resultados
        assertEquals("publicacion-academico", vista);
    }

    @Test
    public void testMostrarFormularioPublicacion_NoAutorizado() {
        // Configurar sesión sin tipo de usuario
        when(session.getAttribute("tipoUsuario")).thenReturn(null);

        // Ejecutar método
        String vista = publicacionController.mostrarFormularioPublicacion(session);

        // Verificar resultados
        assertEquals("redirect:/login", vista);
    }

    @Test
    public void testCrearPublicacion_Success() throws Exception {
        // Configurar sesión
        when(session.getAttribute("correoUsuario")).thenReturn("academico@ubb.cl");
        when(academicoService.buscarPorCorreo("academico@ubb.cl")).thenReturn(academicoPrueba);

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
        ResponseEntity<?> response = publicacionController.crearPublicacion(
            "Título Publicación", 
            "Descripción de la publicación", 
            "Categoría", 
            imagen, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicacionService).crearPublicacion(any(PublicacionAcademico.class), any(Academico.class));
    }

    @Test
    public void testCrearPublicacion_ImagenInvalida() throws Exception {
        // Configurar sesión
        when(session.getAttribute("correoUsuario")).thenReturn("academico@ubb.cl");
        when(academicoService.buscarPorCorreo("academico@ubb.cl")).thenReturn(academicoPrueba);

        // Simular archivo no válido
        MockMultipartFile imagen = new MockMultipartFile(
            "imagen", 
            "test.txt", 
            "text/plain", 
            "contenido no imagen".getBytes()
        );

        // Ejecutar método
        ResponseEntity<?> response = publicacionController.crearPublicacion(
            "Título Publicación", 
            "Descripción de la publicación", 
            "Categoría", 
            imagen, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testMostrarPublicacionesPublicas() {
        // Preparar datos de prueba
        PublicacionAcademico publicacion1 = new PublicacionAcademico();
        publicacion1.setNomNoticiaAcademico("Publicación 1");
        
        PublicacionAcademico publicacion2 = new PublicacionAcademico();
        publicacion2.setNomNoticiaAcademico("Publicación 2");

        // Simular servicio
        when(publicacionService.obtenerTodasLasPublicaciones())
            .thenReturn(List.of(publicacion1, publicacion2));

        // Ejecutar método
        String vista = publicacionController.mostrarPublicacionesPublicas(model);

        // Verificar resultados
        assertEquals("publicaciones-academicas", vista);
        verify(model).addAttribute("publicaciones", List.of(publicacion1, publicacion2));
    }

    @Test
    public void testCrearPublicacion_SinImagen() throws Exception {
        // Configurar sesión
        when(session.getAttribute("correoUsuario")).thenReturn("academico@ubb.cl");
        when(academicoService.buscarPorCorreo("academico@ubb.cl")).thenReturn(academicoPrueba);

        // Ejecutar método sin imagen
        ResponseEntity<?> response = publicacionController.crearPublicacion(
            "Título Publicación", 
            "Descripción de la publicación", 
            "Categoría", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicacionService).crearPublicacion(any(PublicacionAcademico.class), any(Academico.class));
    }

    @Test
    public void testCrearPublicacion_UsuarioNoAutenticado() throws Exception {
        // Configurar sesión sin usuario
        when(session.getAttribute("correoUsuario")).thenReturn(null);

        // Ejecutar método
        ResponseEntity<?> response = publicacionController.crearPublicacion(
            "Título Publicación", 
            "Descripción de la publicación", 
            "Categoría", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuario no autenticado", response.getBody());
    }
}