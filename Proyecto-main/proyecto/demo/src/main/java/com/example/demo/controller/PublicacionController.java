package com.example.demo.controller;

import com.example.demo.model.Publicacion; 
import com.example.demo.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @PostMapping("/crear")
    public ResponseEntity<Publicacion> crearPublicacion(@RequestBody Publicacion publicacion) { // Cambiar a 'Publicacion'
        Publicacion nuevaPublicacion = publicacionService.crearPublicacion(publicacion);
        return new ResponseEntity<>(nuevaPublicacion, HttpStatus.CREATED);
    }
}

