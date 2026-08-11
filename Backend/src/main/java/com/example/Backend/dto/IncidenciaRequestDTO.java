package com.example.Backend.dto;

import com.example.Backend.model.Prioridad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidenciaRequestDTO(
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    String titulo,

    @NotBlank(message = "La descripción es obligatoria")
    String descripcion,

    @NotBlank(message = "La categoría es obligatoria")
    String categoria,

    @NotNull(message = "La prioridad es obligatoria")
    Prioridad prioridad
) {}