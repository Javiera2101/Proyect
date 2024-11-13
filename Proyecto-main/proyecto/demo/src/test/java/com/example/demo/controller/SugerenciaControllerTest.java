package com.example.demo.controller;

import com.example.demo.model.Sugerencia;
import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.repository.SugerenciaRepository;
import com.example.demo.repository.AcademicoRepository;
import com.example.demo.repository.EstudianteRepository;
import com.example.demo.repository.PoloRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SugerenciaControllerTest {

    @Mock
    private SugerenciaRepository sugerenciaRepository;

    @Mock
    private AcademicoRepository academicoRepository;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private PoloRepository poloRepository;

    @InjectMocks
    private SugerenciaController sugerenciaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sugerenciaController).build();
    }

    @Test
    void testCrearSugerencia_Success() throws Exception {
        // Crear objetos mock para las entidades relacionadas
        Estudiante estudiante = new Estudiante();
        estudiante.setIdEstudiante(1);
        
        Academico academico = new Academico();
        academico.setIdAcademico(1);

        Polo polo = new Polo();
        polo.setIdPolo(1);

        Sugerencia sugerencia = new Sugerencia("Pendiente", "Sugerencia 1", "Descripción de la sugerencia", estudiante, academico, polo);

        // Definir el comportamiento del mock para los repositorios
        when(academicoRepository.findById(1)).thenReturn(java.util.Optional.of(academico));
        when(estudianteRepository.findById(1)).thenReturn(java.util.Optional.of(estudiante));
        when(poloRepository.findById(1)).thenReturn(java.util.Optional.of(polo));
        when(sugerenciaRepository.save(any(Sugerencia.class))).thenReturn(sugerencia);  // Cambiado a any(Sugerencia.class)

        // Realizar la solicitud POST a la API
        mockMvc.perform(post("/api/sugerencias")
                .param("academicoId", "1")
                .param("estudianteId", "1")
                .param("poloId", "1")
                .contentType("application/json")
                .content("{\"estado\":\"Pendiente\",\"nombreSugerencia\":\"Sugerencia 1\",\"descripcion\":\"Descripción de la sugerencia\"}"))
                .andExpect(status().isOk());

        // Verificar que el método save fue llamado una vez
        verify(sugerenciaRepository, times(1)).save(any(Sugerencia.class)); // Cambiado a any(Sugerencia.class)
    }

    @Test
    void testCrearSugerencia_BadRequest() throws Exception {
        // Definir comportamiento de los mocks para devolver null si alguno de los objetos no se encuentra
        when(academicoRepository.findById(1)).thenReturn(java.util.Optional.empty());
        when(estudianteRepository.findById(1)).thenReturn(java.util.Optional.empty());
        when(poloRepository.findById(1)).thenReturn(java.util.Optional.empty());

        // Realizar la solicitud POST a la API
        mockMvc.perform(post("/api/sugerencias")
                .param("academicoId", "1")
                .param("estudianteId", "1")
                .param("poloId", "1")
                .contentType("application/json")
                .content("{\"estado\":\"Pendiente\",\"nombreSugerencia\":\"Sugerencia 1\",\"descripcion\":\"Descripción de la sugerencia\"}"))
                .andExpect(status().isBadRequest());
    }
}
