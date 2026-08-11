package com.example.Backend.dto;

public record ResumenEstadoDTO(
    long abiertas,
    long enProgreso,
    long resueltas,
    long total
) {}