package com.example.demo.controller;

import com.example.demo.model.Sugerencia;
import com.example.demo.model.Academico;
import com.example.demo.model.Estudiante;
import com.example.demo.model.Polo;
import com.example.demo.repository.SugerenciaRepository;
import com.example.demo.repository.AcademicoRepository;
import com.example.demo.repository.EstudianteRepository;
import com.example.demo.repository.PoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sugerencias")
public class SugerenciaController {

    @Autowired
    private SugerenciaRepository sugerenciaRepository;

    @Autowired
    private AcademicoRepository academicoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PoloRepository poloRepository;

    @PostMapping
    public ResponseEntity<Sugerencia> crearSugerencia(
            @RequestBody Sugerencia sugerencia,
            @RequestParam Integer academicoId,
            @RequestParam Integer estudianteId,
            @RequestParam Integer poloId) {

        Academico academico = academicoRepository.findById(academicoId).orElse(null);
        Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);
        Polo polo = poloRepository.findById(poloId).orElse(null);

        if (academico == null || estudiante == null || polo == null) {
            return ResponseEntity.badRequest().build();
        }

        sugerencia.setAcademico(academico);
        sugerencia.setEstudiante(estudiante);
        sugerencia.setPolo(polo);

        Sugerencia nuevaSugerencia = sugerenciaRepository.save(sugerencia);
        return ResponseEntity.ok(nuevaSugerencia);
    }
}
