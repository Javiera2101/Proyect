package com.example.demo.service;

import com.example.demo.model.Publicacion;
import com.example.demo.repository.PublicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PublicacionServiceTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionService publicacionService;

    private Publicacion publicacion;

    @BeforeEach
    public void setUp() {
        publicacion = new Publicacion("Publicación de prueba", "Gestionar", "Categoria1", null);
    }

    @Test
    public void testCrearPublicacion() {
        // Configuración de lo que debería hacer el mock
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        Publicacion result = publicacionService.crearPublicacion(publicacion);

        assertNotNull(result);
        assertEquals("Publicación de prueba", result.getNomPublicacion());
        verify(publicacionRepository, times(1)).save(publicacion);
    }

    @Test
    public void testObtenerPublicaciones() {
        // Configuración de lo que debería hacer el mock
        when(publicacionRepository.findAll()).thenReturn(Arrays.asList(publicacion));

        var result = publicacionService.obtenerPublicaciones();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(publicacionRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerPublicacionPorId() {
        // Configuración de lo que debería hacer el mock
        when(publicacionRepository.findById(1L)).thenReturn(Optional.of(publicacion));

        Optional<Publicacion> result = publicacionService.obtenerPublicacionPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Publicación de prueba", result.get().getNomPublicacion());
        verify(publicacionRepository, times(1)).findById(1L);
    }

    @Test
    public void testEditarPublicacion() {
        Publicacion detallesPublicacion = new Publicacion("Publicación actualizada", "Gestionar actualizado", "Categoria2", null);

        when(publicacionRepository.findById(1L)).thenReturn(Optional.of(publicacion));
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        Publicacion result = publicacionService.editarPublicacion(1L, detallesPublicacion);

        assertNotNull(result);
        assertEquals("Publicación actualizada", result.getNomPublicacion());
        assertEquals("Gestionar actualizado", result.getGestionar());
        assertEquals("Categoria2", result.getCategoria());
        verify(publicacionRepository, times(1)).findById(1L);
        verify(publicacionRepository, times(1)).save(publicacion);
    }

    @Test
    public void testEliminarPublicacion() {
        when(publicacionRepository.existsById(1L)).thenReturn(true);

        publicacionService.eliminarPublicacion(1L);

        verify(publicacionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarPublicacionNoExistente() {
        when(publicacionRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            publicacionService.eliminarPublicacion(1L);
        });

        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicacionRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testObtenerPublicacionesPorCategoria() {
        when(publicacionRepository.findByCategoria("Categoria1")).thenReturn(Arrays.asList(publicacion));

        var result = publicacionService.obtenerPublicacionesPorCategoria("Categoria1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Categoria1", result.get(0).getCategoria());
        verify(publicacionRepository, times(1)).findByCategoria("Categoria1");
    }

    @Test
    public void testObtenerPublicacionesPorFecha() {
        LocalDate fecha = LocalDate.now();
        when(publicacionRepository.findByFechaPublicacion(fecha)).thenReturn(Arrays.asList(publicacion));

        var result = publicacionService.obtenerPublicacionesPorFecha(fecha);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fecha, result.get(0).getFechaPublicacion());
        verify(publicacionRepository, times(1)).findByFechaPublicacion(fecha);
    }

    @Test
    public void testObtenerPublicacionesPorRangoFechas() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(publicacionRepository.findByFechaPublicacionBetween(startDate, endDate)).thenReturn(Arrays.asList(publicacion));

        var result = publicacionService.obtenerPublicacionesPorRangoFechas(startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(publicacionRepository, times(1)).findByFechaPublicacionBetween(startDate, endDate);
    }
}

