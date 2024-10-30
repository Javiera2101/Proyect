package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Academico;
import com.example.demo.service.AcademicoService;

@RestController
@RequestMapping("/academico")
public class AcademicoController {
    @Autowired
    AcademicoService academicoService;

    @GetMapping("")
    public List<Academico> list() {
        return academicoService.buscarTodosLosAcademicos();
    }

    @PostMapping("/registroAcademico")
    public ResponseEntity<Academico> registrarAcademico(@RequestBody Academico academico) {
        try {
            Academico nuevoAcademico = academicoService.registrarAcademico(academico);
            return new ResponseEntity<>(nuevoAcademico, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
