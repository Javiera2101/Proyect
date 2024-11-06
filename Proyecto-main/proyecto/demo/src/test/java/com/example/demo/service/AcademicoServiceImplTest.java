package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.model.Academico;
import com.example.demo.repository.AcademicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

public class AcademicoServiceImplTest {

    @Mock
    private AcademicoRepository academicoRepository;

    @InjectMocks
    private AcademicoServiceImpl academicoService;

    private Academico academico;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
        academico = new Academico();
        academico.setIdAcademico(1);
        academico.setNomAcademico("Juan Perez");
        academico.setCorreoUbb("juan.perez@ubb.cl");
        academico.setContrasenaAcademico("password123");
        academico.setDepartamento("Informatica");
    }

    @Test
    void testBuscarTodosLosAcademicos() {
        // Configuramos el comportamiento del mock
        when(academicoRepository.findAll()).thenReturn(Arrays.asList(academico));

        // Ejecutamos el método
        var result = academicoService.buscarTodosLosAcademicos();

        // Verificamos los resultados
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan Perez", result.get(0).getNomAcademico());

        // Verificamos que el mock fue llamado correctamente
        verify(academicoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarAcademicoPorId() {
        // Configuramos el comportamiento del mock
        when(academicoRepository.findById(1)).thenReturn(Optional.of(academico));

        // Ejecutamos el método
        var result = academicoService.buscarAcademicoPorId(1);

        // Verificamos el resultado
        assertNotNull(result);
        assertEquals("Juan Perez", result.getNomAcademico());

        // Verificamos que el mock fue llamado correctamente
        verify(academicoRepository, times(1)).findById(1);
    }

    @Test
    void testRegistrarAcademico() {
        // Configuramos el comportamiento del mock
        when(academicoRepository.save(any(Academico.class))).thenReturn(academico);

        // Ejecutamos el método
        var result = academicoService.registrarAcademico(academico);

        // Verificamos el resultado
        assertNotNull(result);
        assertEquals("Juan Perez", result.getNomAcademico());
        assertEquals("juan.perez@ubb.cl", result.getCorreoUbb());

        // Verificamos que el mock fue llamado correctamente
        verify(academicoRepository, times(1)).save(academico);
    }

    @Test
    void testBorrarAcademicoPorId() {
        // Configuramos el comportamiento del mock
        doNothing().when(academicoRepository).deleteById(1);

        // Ejecutamos el método
        academicoService.BorrarAcademicoPorId(1);

        // Verificamos que el mock fue llamado correctamente
        verify(academicoRepository, times(1)).deleteById(1);
    }

    @Test
    
public void testRegistrarAcademicoConCorreoInvalido() {
    // Preparar un objeto Academico con un correo no válido
    Academico academicoInvalido = new Academico();
    academicoInvalido.setCorreoUbb("invalidemail@domain.com");

    // Verificar que se lanza la excepción IllegalArgumentException
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        academicoService.registrarAcademico(academicoInvalido);
    });

    assertEquals("El correo debe ser institucional (@ubb.cl)", exception.getMessage());
}

}

