package com.example.Backend.dto;


import java.time.LocalDateTime;
import com.example.Backend.model.Estado;
import com.example.Backend.model.Prioridad;

public record IncidenciaResponseDTO(
    Long id,
    String titulo,
    String descripcion,
    String categoria,
    Prioridad prioridad,
    Estado estado,
    LocalDateTime fechaCreacion
) {}