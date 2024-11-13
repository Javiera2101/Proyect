package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Publicacion;
import com.example.demo.repository.PublicacionRepository;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PublicacionServiceTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionService publicacionService;

    private Publicacion publicacion;

    @BeforeEach
    public void setUp() {
        // Configuración inicial antes de cada test
        publicacion = new Publicacion();
        publicacion.setIdPublicacion(1L);
        publicacion.setNomPublicacion("Test Title");
        publicacion.setGestionar("Test Content");
    }

    @Test
    public void testGuardarPublicacion() {
        // Configurar el comportamiento esperado del mock
        when(publicacionRepository.save(any(Publicacion.class))).thenReturn(publicacion);

        // Llamar al método de servicio
        Publicacion result = publicacionService.guardarPublicacion(publicacion);

        // Verificar que el repositorio se haya llamado correctamente
        verify(publicacionRepository, times(1)).save(publicacion);

        // Verificar que el resultado es el esperado
        assertNotNull(result);
        assertEquals("Test Title", result.getNomPublicacion());
    }

    @Test
    
    public void testObtenerPublicacionPorId() {
        // Configurar el comportamiento esperado del mock
        when(publicacionRepository.findById(1L)).thenReturn(Optional.of(publicacion));

        // Llamar al método de servicio
        Optional<Publicacion> resultOptional = publicacionService.obtenerPublicacionPorId(1L);

        // Verificar que el repositorio se haya llamado correctamente
        verify(publicacionRepository, times(1)).findById(1L);

        // Verificar que el resultado es el esperado
        assertTrue(resultOptional.isPresent());  // Verifica que el Optional tiene un valor
        Publicacion result = resultOptional.get();  // Obtiene el valor dentro del Optional

        assertEquals(1L, result.getIdPublicacion());
        assertEquals("Test Title", result.getNomPublicacion());
}

    @Test
        public void testObtenerPublicacionPorId_NoExiste() {
        // Configurar el comportamiento del mock para devolver un valor vacío
        when(publicacionRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al método de servicio
        Optional<Publicacion> resultOptional = publicacionService.obtenerPublicacionPorId(1L);

        // Verificar que el repositorio se haya llamado correctamente
        verify(publicacionRepository, times(1)).findById(1L);

        // Verificar que el resultado sea un Optional vacío
        assertFalse(resultOptional.isPresent()); // Verifica que el Optional esté vacío
    }


    @Test
public void testEliminarPublicacion() {
    // Configurar el comportamiento esperado del mock para que encuentre la publicación
    when(publicacionRepository.existsById(1L)).thenReturn(true);
    doNothing().when(publicacionRepository).deleteById(1L);

    // Llamar al método de servicio
    publicacionService.eliminarPublicacion(1L);

    // Verificar que el repositorio se haya llamado correctamente
    verify(publicacionRepository, times(1)).deleteById(1L);
}

}
