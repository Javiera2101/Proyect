package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.LoginService;

import jakarta.servlet.http.HttpSession;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() throws Exception {
        // Datos de prueba
        Map<String, String> loginData = new HashMap<>();
        loginData.put("correo", "test@ubb.cl");
        loginData.put("contrasena", "password123");

        // Simular el comportamiento del servicio
        when(loginService.autenticarUsuario("test@ubb.cl", "password123")).thenReturn("estudiante");
        when(loginService.obtenerNombreUsuario("test@ubb.cl")).thenReturn("Test User");

        // Llamar al método login
        ResponseEntity<?> response = loginController.login(loginData, session);

        // Verificar que se establecieron los atributos de sesión correctamente
        verify(session).setAttribute("tipoUsuario", "estudiante");
        verify(session).setAttribute("nombreUsuario", "Test User");
        verify(session).setAttribute("correoUsuario", "test@ubb.cl");

        // Verificar que la respuesta sea correcta
        assertEquals(ResponseEntity.ok().body(Map.of("redirectUrl", "/")), response);
    }

    @Test
    public void testLogin_Failure() throws Exception {
        // Datos de prueba
        Map<String, String> loginData = new HashMap<>();
        loginData.put("correo", "test@ubb.cl");
        loginData.put("contrasena", "wrongpassword");

        // Simular una excepción en el servicio
        when(loginService.autenticarUsuario("test@ubb.cl", "wrongpassword"))
                .thenThrow(new Exception("Credenciales inválidas"));

        // Llamar al método login
        ResponseEntity<?> response = loginController.login(loginData, session);

        // Verificar que no se establecieron atributos en la sesión
        verify(session, never()).setAttribute(anyString(), any());

        // Verificar que la respuesta contenga el mensaje de error
        assertEquals(ResponseEntity.badRequest().body(Map.of("error", "Credenciales inválidas")), response);
    }

    @Test
    public void testLogout() {
        // Llamar al método logout
        ResponseEntity<?> response = loginController.logout(session);

        // Verificar que la sesión se invalide
        verify(session).invalidate();

        // Verificar que la respuesta sea correcta
        assertEquals(ResponseEntity.ok().body(Map.of("redirectUrl", "/")), response);
    }

    @Test
    public void testVerificarSesion_Active() {
        // Simular una sesión activa
        when(session.getAttribute("tipoUsuario")).thenReturn("estudiante");

        // Llamar al método verificarSesion
        Map<String, Object> response = loginController.verificarSesion(session);

        // Verificar que la respuesta sea correcta
        assertEquals(true, response.get("sesionActiva"));
        assertEquals("estudiante", response.get("tipoUsuario"));
    }

    @Test
    public void testVerificarSesion_Inactive() {
        // Simular una sesión inactiva
        when(session.getAttribute("tipoUsuario")).thenReturn(null);

        // Llamar al método verificarSesion
        Map<String, Object> response = loginController.verificarSesion(session);

        // Verificar que la respuesta sea correcta
        assertEquals(false, response.get("sesionActiva"));
        assertEquals("", response.get("tipoUsuario"));
    }
}