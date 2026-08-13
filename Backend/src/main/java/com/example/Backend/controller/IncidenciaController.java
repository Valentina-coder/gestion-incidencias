package com.example.Backend.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Backend.dto.IncidenciaRequestDTO;
import com.example.Backend.dto.IncidenciaResponseDTO;
import com.example.Backend.dto.ResumenEstadoDTO;
import com.example.Backend.model.Estado;
import com.example.Backend.model.Prioridad;
import com.example.Backend.service.IncidenciaService;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService service;

    public IncidenciaController(IncidenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IncidenciaResponseDTO>> listar(
            @RequestParam(required = false) Estado estado,
            @RequestParam(required = false) Prioridad prioridad,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.listarTodos(estado, prioridad, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<IncidenciaResponseDTO> crear(@Valid @RequestBody IncidenciaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidenciaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody IncidenciaRequestDTO request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<IncidenciaResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Estado nuevoEstado) {
        return ResponseEntity.ok(service.cambiarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumen")
    public ResponseEntity<ResumenEstadoDTO> obtenerResumen() {
        return ResponseEntity.ok(service.obtenerResumen());
    }
}