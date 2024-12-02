
package com.example.demo.service;

import com.example.demo.model.publicación;
import com.example.demo.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;

    public PublicacionService(PublicacionRepository publicacionRepository) {
        this.publicacionRepository = publicacionRepository;
    }

    public publicación crearPublicacion(publicación publicacion) {
        publicacion.setFechaPublicacion(LocalDate.now()); // Fecha de publicación como el día actual
        return publicacionRepository.save(publicacion);
    }

    public List<publicación> obtenerPublicaciones() {
        return publicacionRepository.findAll();
    }

    public Optional<publicación> obtenerPublicacionPorId(Long id) {
        return publicacionRepository.findById(id);
    }

    public void eliminarPublicacion(Long id) {
        publicacionRepository.deleteById(id);
    }
}