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

import com.example.demo.model.Academico;
import com.example.demo.service.AcademicoService;

public class AcademicoControllerTest {

    @InjectMocks
    private AcademicoController academicoController;

    @Mock
    private AcademicoService academicoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testList() {
        // Datos de prueba
        List<Academico> listaAcademicos = new ArrayList<>();
        listaAcademicos.add(new Academico());

        // Simular el comportamiento del servicio
        when(academicoService.buscarTodosLosAcademicos()).thenReturn(listaAcademicos);

        // Llamar al método list
        List<Academico> response = academicoController.list();

        // Verificar que el servicio fue llamado
        verify(academicoService).buscarTodosLosAcademicos();

        // Verificar que la respuesta sea la esperada
        assertEquals(listaAcademicos, response);
    }

    @Test
    public void testRegistrarAcademico_Success() {
        // Datos de prueba
        Academico academico = new Academico();
        academico.setNomAcademico("Test Academico");
        academico.setCorreoUbb("test@ubb.cl");
        academico.setContrasenaAcademico("password123");
        academico.setDepartamento("Ciencias");

        // Simular el comportamiento del servicio
        when(academicoService.registrarAcademico(academico)).thenReturn(academico);

        // Llamar al método registrarAcademico
        ResponseEntity<Academico> response = academicoController.registrarAcademico(academico);

        // Verificar que el servicio fue llamado
        verify(academicoService).registrarAcademico(academico);

        // Verificar que la respuesta sea la esperada
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(academico, response.getBody());
    }

    @Test
    public void testRegistrarAcademico_Failure() {
        // Datos de prueba
        Academico academico = new Academico();
        academico.setNomAcademico("Test Academico");
        academico.setCorreoUbb("test@ubb.cl");
        academico.setContrasenaAcademico("password123");
        academico.setDepartamento("Ciencias");

        // Simular el comportamiento del servicio lanzando una excepción
        when(academicoService.registrarAcademico(academico)).thenThrow(new IllegalArgumentException("Error en el registro"));

        // Llamar al método registrarAcademico y verificar que lanza la excepción
        try {
            academicoController.registrarAcademico(academico);
        } catch (Exception e) {
            // Verificar que la excepción lanzada es la esperada
            assertEquals("Error en el registro", e.getMessage());
        }

        // Verificar que el servicio fue llamado
        verify(academicoService).registrarAcademico(academico);
    }
}
