package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.EstudianteService;
import com.example.demo.service.PoloService;

public class RegistroControllerTest {

    @InjectMocks
    private RegistroController registroController;

    @Mock
    private AcademicoService academicoService;

    @Mock
    private EstudianteService estudianteService;

    @Mock
    private PoloService poloService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistrarUsuario_RegistroAcademico() {
        // Datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "academico");
        datos.put("nombre", "Juan Perez");
        datos.put("correo", "juan.perez@ubb.cl");
        datos.put("contrasena", "password123");
        datos.put("departamento", "Ciencias");

        // Simular el comportamiento del servicio
        Academico academico = new Academico();
        when(academicoService.registrarAcademico(any(Academico.class))).thenReturn(academico);

        // Llamar al método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registro exitoso", response.getBody());
        verify(academicoService, times(1)).registrarAcademico(any(Academico.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistrarUsuario_RegistroEstudiante() {
        // Datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "estudiante");
        datos.put("nombre", "Maria Lopez");
        datos.put("correo", "maria.lopez@ubb.cl");
        datos.put("contrasena", "password123");
        datos.put("carrera", "Ingeniería");

        // Simular el comportamiento del servicio
        Estudiante estudiante = new Estudiante();
        when(estudianteService.registrarEstudiante(any(Estudiante.class))).thenReturn(estudiante);

        // Llamar al método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registro exitoso", response.getBody());
        verify(estudianteService, times(1)).registrarEstudiante(any(Estudiante.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistrarUsuario_RegistroPolo() {
        // Datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "polo");
        datos.put("nombre", "Polo A");
        datos.put("correo", "polo.a@ubb.cl");
        datos.put("contrasena", "password123");
        datos.put("numTelefono", "123456789");

        // Simular el comportamiento del servicio
        Polo polo = new Polo();
        when(poloService.registrarPolo(any(Polo.class))).thenReturn(polo);

        // Llamar al método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registro exitoso", response.getBody());
        verify(poloService, times(1)).registrarPolo(any(Polo.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistrarUsuario_TestFallido() {
        // Datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "invalido");
        
        // Llamar al método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultados
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Tipo de usuario no válido", response.getBody());
    }
}