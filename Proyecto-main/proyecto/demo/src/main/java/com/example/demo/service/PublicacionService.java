package com.example.demo.service;

import com.example.demo.model.Publicacion; // Cambiar a 'Publicacion'
import com.example.demo.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear una nueva publicación
    public Publicacion crearPublicacion(Publicacion publicacion) { // Cambiar a 'Publicacion'
        return publicacionRepository.save(publicacion);
    }

    // Obtener todas las publicaciones
    public List<Publicacion> obtenerPublicaciones() { // Cambiar a 'Publicacion'
        return publicacionRepository.findAll();
    }

    // Obtener una publicación por su ID
    public Optional<Publicacion> obtenerPublicacionPorId(Long id) { // Cambiar a 'Publicacion'
        return publicacionRepository.findById(id);
    }

    // Editar una publicación existente
    public Publicacion editarPublicacion(Long id, Publicacion detallesPublicacion) { // Cambiar a 'Publicacion'
        Optional<Publicacion> publicacionExistente = publicacionRepository.findById(id);
        if (publicacionExistente.isPresent()) {
            Publicacion publicacion = publicacionExistente.get();
            publicacion.setNomPublicacion(detallesPublicacion.getNomPublicacion()); // Cambiar a camelCase
            publicacion.setGestionar(detallesPublicacion.getGestionar());
            publicacion.setCategoria(detallesPublicacion.getCategoria());
            return publicacionRepository.save(publicacion);
        }
        throw new RuntimeException("Publicación no encontrada"); // Lanzar excepción si no se encuentra
    }

    // Eliminar una publicación
    public void eliminarPublicacion(Long id) {
        if (publicacionRepository.existsById(id)) {
            publicacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Publicación no encontrada"); // Lanzar excepción si no se encuentra
        }
    }

    // Consultas personalizadas
    public List<Publicacion> obtenerPublicacionesPorCategoria(String categoria) { // Cambiar a 'Publicacion'
        return publicacionRepository.findByCategoria(categoria);
    }

    public List<Publicacion> obtenerPublicacionesPorFecha(LocalDate fecha) { // Cambiar a 'Publicacion'
        return publicacionRepository.findByFechaPublicacion(fecha);
    }

    public List<Publicacion> obtenerPublicacionesPorNombre(String nomPublicacion) { // Cambiar a 'Publicacion'
        return publicacionRepository.findByNomPublicacion(nomPublicacion);
    }

    public List<Publicacion> obtenerPublicacionesPorRangoFechas(LocalDate startDate, LocalDate endDate) { // Cambiar a 'Publicacion'
        return publicacionRepository.findByFechaPublicacionBetween(startDate, endDate);
    }
}
