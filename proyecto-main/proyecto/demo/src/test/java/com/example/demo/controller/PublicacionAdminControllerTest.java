package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import com.example.demo.model.PublicacionAcademico;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.PublicacionAcademicoService;

import jakarta.servlet.http.HttpSession;

public class PublicacionAdminControllerTest {

    @InjectMocks
    private PublicacionAdminController publicacionAdminController;

    @Mock
    private PublicacionAcademicoService publicacionService;

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
    public void testMostrarPublicacionesParaAdministrar_Autorizado() {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");

        // Preparar datos de prueba
        List<PublicacionAcademico> publicaciones = new ArrayList<>();
        PublicacionAcademico publicacion1 = new PublicacionAcademico();
        publicacion1.setNomNoticiaAcademico("Publicación 1");
        publicaciones.add(publicacion1);

        // Simular servicio
        when(publicacionService.obtenerTodasLasPublicaciones()).thenReturn(publicaciones);

        // Ejecutar método
        String vista = publicacionAdminController.mostrarPublicacionesParaAdministrar(model, session);

        // Verificar resultados
        assertEquals("administrar-publicaciones-academicas", vista);
        verify(model).addAttribute("publicaciones", publicaciones);
    }

    @Test
    public void testMostrarPublicacionesParaAdministrar_NoAutorizado() {
        // Configurar sesión sin autorización
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Ejecutar método
        String vista = publicacionAdminController.mostrarPublicacionesParaAdministrar(model, session);

        // Verificar resultados
        assertEquals("redirect:/login", vista);
    }

    @Test
    public void testEliminarPublicacion_Autorizado() {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");

        // Ejecutar método
        ResponseEntity<?> response = publicacionAdminController.eliminarPublicacion(1, session);

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicacionService).eliminarPublicacion(1);
    }

    @Test
    public void testEliminarPublicacion_NoAutorizado() {
        // Configurar sesión sin autorización
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Ejecutar método
        ResponseEntity<?> response = publicacionAdminController.eliminarPublicacion(1, session);

        // Verificar resultados
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testMostrarFormularioEdicion_Autorizado() {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");

        // Preparar datos de prueba
        PublicacionAcademico publicacion = new PublicacionAcademico();
        publicacion.setIdNoticiaAcademico(1);
        publicacion.setNomNoticiaAcademico("Publicación Test");

        // Simular servicio
        when(publicacionService.obtenerPublicacionPorId(1)).thenReturn(publicacion);

        // Ejecutar método
        String vista = publicacionAdminController.mostrarFormularioEdicion(1, model, session);

        // Verificar resultados
        assertEquals("editar-publicacion-academica", vista);
        verify(model).addAttribute("publicacion", publicacion);
    }

    @Test
    public void testMostrarFormularioEdicion_NoAutorizado() {
        // Configurar sesión sin autorización
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Ejecutar método
        String vista = publicacionAdminController.mostrarFormularioEdicion(1, model, session);

        // Verificar resultados
        assertEquals("redirect:/login", vista);
    }

    @Test
    public void testEditarPublicacion_Success() throws Exception {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");

        // Preparar datos de prueba
        PublicacionAcademico publicacionExistente = new PublicacionAcademico();
        publicacionExistente.setIdNoticiaAcademico(1);

        // Simular servicios
        when(publicacionService.obtenerPublicacionPorId(1)).thenReturn(publicacionExistente);

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
        ResponseEntity<?> response = publicacionAdminController.editarPublicacion(
            1, 
            "Nuevo Título", 
            "Nueva Descripción", 
            "Nueva Categoría", 
            imagen, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicacionService).actualizarPublicacion(any(PublicacionAcademico.class));
    }

    @Test
    public void testEditarPublicacion_NoAutorizado() throws Exception {
        // Configurar sesión sin autorización
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Ejecutar método
        ResponseEntity<?> response = publicacionAdminController.editarPublicacion(
            1, 
            "Nuevo Título", 
            "Nueva Descripción", 
            "Nueva Categoría", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testEditarPublicacion_SinImagen() throws Exception {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");
        when(session.getAttribute("correoUsuario")).thenReturn("polo@ubb.cl");

        // Preparar datos de prueba
        PublicacionAcademico publicacionExistente = new PublicacionAcademico();
        publicacionExistente.setIdNoticiaAcademico(1);
        // Simular servicios
        when(publicacionService.obtenerPublicacionPorId(1)).thenReturn(publicacionExistente);

        // Ejecutar método sin imagen
        ResponseEntity<?> response = publicacionAdminController.editarPublicacion(
            1, 
            "Nuevo Título", 
            "Nueva Descripción", 
            "Nueva Categoría", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicacionService).actualizarPublicacion(any(PublicacionAcademico.class));
    }

    @Test
    public void testEditarPublicacion_PublicacionNoEncontrada() throws Exception {
        // Configurar sesión
        when(session.getAttribute("tipoUsuario")).thenReturn("polo");

        // Simular servicio que no encuentra la publicación
        when(publicacionService.obtenerPublicacionPorId(anyInt())).thenReturn(null);

        // Ejecutar método
        ResponseEntity<?> response = publicacionAdminController.editarPublicacion(
            1, 
            "Nuevo Título", 
            "Nueva Descripción", 
            "Nueva Categoría", 
            null, 
            session
        );

        // Verificar resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
