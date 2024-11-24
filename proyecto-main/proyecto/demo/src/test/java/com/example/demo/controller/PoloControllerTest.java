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

import com.example.demo.model.Polo;
import com.example.demo.service.PoloService;

public class PoloControllerTest {

    @InjectMocks
    private PoloController poloController;

    @Mock
    private PoloService poloService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testList() {
        // Datos de prueba
        List<Polo> polos = new ArrayList<>();
        Polo polo1 = new Polo();
        polo1.setNombrePolo("Polo 1");
        polos.add(polo1);

        // Simular el comportamiento del servicio
        when(poloService.buscarPolo()).thenReturn(polos);

        // Llamar al método list
        List<Polo> resultado = poloController.list();

        // Verificar que se llame al servicio y que el resultado sea el esperado
        verify(poloService).buscarPolo();
        assertEquals(polos, resultado);
    }

    @Test
    public void testRegistrarPolo_Success() {
        // Datos de prueba
        Polo nuevoPolo = new Polo();
        nuevoPolo.setNombrePolo("Nuevo Polo");

        // Simular el comportamiento del servicio
        when(poloService.registrarPolo(nuevoPolo)).thenReturn(nuevoPolo);

        // Llamar al método registrarPolo
        ResponseEntity<Polo> response = poloController.registrarPolo(nuevoPolo);

        // Verificar que el estado de la respuesta sea 201 CREATED y que el cuerpo sea el polo creado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(nuevoPolo, response.getBody());
    }

    @Test
    public void testRegistrarPolo_BadRequest() {
        // Datos de prueba
        Polo nuevoPolo = new Polo();
        nuevoPolo.setNombrePolo(null); // Simular un error de validación

        // Llamar al método registrarPolo
        ResponseEntity<Polo> response = poloController.registrarPolo(nuevoPolo);

        // Verificar que el estado de la respuesta sea BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
