package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Estudiante;
import com.example.demo.service.EstudianteService;

public class EstudianteControllerTest {

    @InjectMocks
    private EstudianteController estudianteController;

    @Mock
    private EstudianteService estudianteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testList() {
        // Datos de prueba
        List<Estudiante> estudiantes = new ArrayList<>();
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombreEstudiante("Estudiante 1");
        estudiantes.add(estudiante1);

        // Simular el comportamiento del servicio
        when(estudianteService.buscarTodosLosEstudiantes()).thenReturn(estudiantes);

        // Llamar al método list
        List<Estudiante> resultado = estudianteController.list();

        // Verificar que se llame al servicio y que el resultado sea el esperado
        verify(estudianteService).buscarTodosLosEstudiantes();
        assertEquals(estudiantes, resultado);
    }

    @Test
    public void testRegistrarEstudiante_Success() {
        // Datos de prueba
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setNombreEstudiante("Nuevo Estudiante");

        // Simular el comportamiento del servicio
        when(estudianteService.registrarEstudiante(nuevoEstudiante)).thenReturn(nuevoEstudiante);

        // Llamar al método registrarEstudiante
        ResponseEntity<Estudiante> response = estudianteController.registrarEstudiante(nuevoEstudiante);

        // Verificar que el estado de la respuesta sea 201 CREATED y que el cuerpo sea el estudiante creado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(nuevoEstudiante, response.getBody());
    }

    @Test
    public void testRegistrarEstudiante_BadRequest() {
        // Datos de prueba
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setNombreEstudiante(null); // Simular un error de validación

        // Simular el comportamiento del servicio lanzando una excepción
        when(estudianteService.registrarEstudiante(nuevoEstudiante)).thenThrow(new IllegalArgumentException("Nombre no puede ser nulo"));

        // Llamar al método registrarEstudiante y verificar que lanza una excepción
        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            estudianteController.registrarEstudiante(nuevoEstudiante);
        });

        // Verificar que la excepción tenga el estado BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Nombre no puede ser nulo", exception.getReason());
    }
}