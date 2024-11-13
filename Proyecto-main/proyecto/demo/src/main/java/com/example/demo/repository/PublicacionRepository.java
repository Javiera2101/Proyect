package com.example.demo.repository;

import com.example.demo.model.Publicacion; // Cambiar a 'Publicacion'
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> { // Cambiar a 'Publicacion'
    List<Publicacion> findByCategoria(String categoria); // Cambiar a 'Publicacion'

    List<Publicacion> findByFechaPublicacion(LocalDate fecha); // Cambiar a 'Publicacion'

    List<Publicacion> findByNomPublicacion(String nomPublicacion); // Cambiar a 'Publicacion'

    List<Publicacion> findByFechaPublicacionBetween(LocalDate startDate, LocalDate endDate); // Cambiar a 'Publicacion'
}
