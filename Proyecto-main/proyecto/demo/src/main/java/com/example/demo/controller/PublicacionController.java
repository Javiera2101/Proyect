
package com.example.demo.controller;

import com.example.demo.model.publicación;
import com.example.demo.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    private final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    // Endpoint para crear una publicación
    @PostMapping
    public ResponseEntity<publicación> crearPublicacion(@RequestBody publicación publicacion) {
        publicación nuevaPublicacion = publicacionService.crearPublicacion(publicacion);
        return ResponseEntity.ok(nuevaPublicacion);
    }

    // Endpoint para obtener todas las publicaciones
    @GetMapping
    public ResponseEntity<List<publicación>> obtenerPublicaciones() {
        return ResponseEntity.ok(publicacionService.obtenerPublicaciones());
    }

    // Endpoint para obtener una publicación por su ID
    @GetMapping("/{id}")
    public ResponseEntity<publicación> obtenerPublicacionPorId(@PathVariable Long id) {
        return publicacionService.obtenerPublicacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para eliminar una publicación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        publicacionService.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }
}