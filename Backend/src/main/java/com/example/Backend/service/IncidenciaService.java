package com.example.Backend.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Backend.dto.IncidenciaRequestDTO;
import com.example.Backend.dto.IncidenciaResponseDTO;
import com.example.Backend.dto.ResumenEstadoDTO;
import com.example.Backend.exception.ResourceNotFoundException;
import com.example.Backend.model.Estado;
import com.example.Backend.model.Incidencia;
import com.example.Backend.model.Prioridad;
import com.example.Backend.repository.IncidenciaRepository;

import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository repository;

    public IncidenciaService(IncidenciaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<IncidenciaResponseDTO> listarTodos(Estado estado, Prioridad prioridad, String search) {
        return repository.buscarConFiltros(estado, prioridad, search)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public IncidenciaResponseDTO obtenerPorId(Long id) {
        Incidencia inc = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con id: " + id));
        return toResponseDTO(inc);
    }

    @Transactional
    public IncidenciaResponseDTO crear(IncidenciaRequestDTO request) {
        Incidencia inc = new Incidencia();
        inc.setTitulo(request.titulo());
        inc.setDescripcion(request.descripcion());
        inc.setCategoria(request.categoria());
        inc.setPrioridad(request.prioridad());
        inc.setEstado(Estado.ABIERTA);

        return toResponseDTO(repository.save(inc));
    }

    @Transactional
    public IncidenciaResponseDTO actualizar(Long id, IncidenciaRequestDTO request) {
        Incidencia inc = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con id: " + id));

        inc.setTitulo(request.titulo());
        inc.setDescripcion(request.descripcion());
        inc.setCategoria(request.categoria());
        inc.setPrioridad(request.prioridad());

        return toResponseDTO(repository.save(inc));
    }

    @Transactional
    public IncidenciaResponseDTO cambiarEstado(Long id, Estado nuevoEstado) {
        Incidencia inc = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con id: " + id));

        inc.setEstado(nuevoEstado);
        return toResponseDTO(repository.save(inc));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Incidencia no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ResumenEstadoDTO obtenerResumen() {
        long abiertas = repository.countByEstado(Estado.ABIERTA);
        long enProgreso = repository.countByEstado(Estado.EN_PROGRESO);
        long resueltas = repository.countByEstado(Estado.RESUELTA);
        long total = repository.count();
        return new ResumenEstadoDTO(abiertas, enProgreso, resueltas, total);
    }

    private IncidenciaResponseDTO toResponseDTO(Incidencia inc) {
        return new IncidenciaResponseDTO(
                inc.getId(),
                inc.getTitulo(),
                inc.getDescripcion(),
                inc.getCategoria(),
                inc.getPrioridad(),
                inc.getEstado(),
                inc.getFechaCreacion()
        );
    }
}