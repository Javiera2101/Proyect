package com.example.demo.controller;

import java.util.HashMap;
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

import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.security.PasswordHasher;
import com.example.demo.service.AcademicoService;
import com.example.demo.service.EmailValidationService;
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

    @Mock
    private EmailValidationService emailValidationService;

    @Mock
    private PasswordHasher passwordHasher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarAcademico_Success() {
        // Preparar datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "academico");
        datos.put("nombre", "Test Academico");
        datos.put("correo", "test@ubb.cl");
        datos.put("contrasena", "password123");
        datos.put("departamento", "Ciencias");

        // Simular comportamiento de servicios
        when(emailValidationService.isEmailValid(datos.get("correo"))).thenReturn(true);
        when(passwordHasher.hashPassword(datos.get("contrasena"))).thenReturn("hashedPassword");

        // Ejecutar método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(academicoService).registrarAcademico(any(Academico.class));
    }

    @Test
    public void testRegistrarEstudiante_Success() {
        // Preparar datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "estudiante");
        datos.put("nombre", "Test Estudiante");
        datos.put("correo", "estudiante@ubb.cl");
        datos.put("contrasena", "password123");
        datos.put("carrera", "Ingeniería");

        // Simular comportamiento de servicios
        when(emailValidationService.isEmailValid(datos.get("correo"))).thenReturn(true);
        when(passwordHasher.hashPassword(datos.get("contrasena"))).thenReturn("hashedPassword");

        // Ejecutar método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(estudianteService).registrarEstudiante(any(Estudiante.class));
    }

    @Test
    public void testRegistrarPolo_Success() {
        // Preparar datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "polo");
        datos.put("nombre", "Test Polo");
        datos.put("correo", "polo@ubb.cl");
        datos.put("contrasena", "password123");
        datos.put("numTelefono", "123456789");

        // Simular comportamiento de servicios
        when(emailValidationService.isEmailValid(datos.get("correo"))).thenReturn(true);
        when(passwordHasher.hashPassword(datos.get("contrasena"))).thenReturn("hashedPassword");

        // Ejecutar método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(poloService).registrarPolo(any(Polo.class));
    }

    @Test
    public void testRegistrar_InvalidEmail() {
        // Preparar datos de prueba
        Map<String, String> datos = new HashMap<>();
        datos.put("tipoUsuario", "academico");
        datos.put("correo", "invalid@email");

        // Simular email inválido
        when(emailValidationService.isEmailValid(datos.get("correo"))).thenReturn(false);

        // Ejecutar método
        ResponseEntity<?> response = registroController.registrarUsuario(datos);

        // Verificar resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
