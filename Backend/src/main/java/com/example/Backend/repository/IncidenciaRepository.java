package com.example.Backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Backend.model.Estado;
import com.example.Backend.model.Incidencia;
import com.example.Backend.model.Prioridad;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    @Query("""
        SELECT i FROM Incidencia i
        WHERE (:estado IS NULL OR i.estado = :estado)
          AND (:prioridad IS NULL OR i.prioridad = :prioridad)
          AND (:search IS NULL OR LOWER(i.titulo) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :search, '%')))
        ORDER BY i.fechaCreacion DESC
    """)
    List<Incidencia> buscarConFiltros(
        @Param("estado") Estado estado,
        @Param("prioridad") Prioridad prioridad,
        @Param("search") String search
    );

    long countByEstado(Estado estado);
}