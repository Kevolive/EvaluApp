package com.cesde.proyecto_integrador.controller;

import com.cesde.proyecto_integrador.model.Pregunta;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.dto.PreguntaRequestDTO;
import com.cesde.proyecto_integrador.repository.PreguntaRepository;
import com.cesde.proyecto_integrador.repository.ExamenRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/preguntas")
@Tag(name = "Preguntas", description = "API para gestionar preguntas de los exámenes")
public class PreguntaController {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private ExamenRepository examenRepository;

    @Operation(summary = "Obtener todas las preguntas")
    @GetMapping
    public ResponseEntity<List<Pregunta>> getAllPreguntas() {
        return ResponseEntity.ok(preguntaRepository.findAll());
    }

    @Operation(summary = "Obtener una pregunta por ID")
    @ApiResponse(responseCode = "200", description = "Pregunta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pregunta.class)))
    @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Pregunta> getPreguntaById(@PathVariable Long id) {
        Optional<Pregunta> pregunta = preguntaRepository.findById(id);
        return pregunta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva pregunta")
    @PostMapping
    public ResponseEntity<Pregunta> createPregunta(@Valid @RequestBody PreguntaRequestDTO dto) {
        Examen examen = examenRepository.findById(dto.getExamenId())
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con ID: " + dto.getExamenId()));

        Pregunta pregunta = new Pregunta();
        pregunta.setTextoPregunta(dto.getTextoPregunta());
        pregunta.setTipoPregunta(dto.getTipoPregunta());
        pregunta.setExamen(examen);
        // Si usás puntos en el modelo real, también se puede setear acá

        return ResponseEntity.ok(preguntaRepository.save(pregunta));
    }

    @Operation(summary = "Actualizar una pregunta existente")
    @PutMapping("/{id}")
    public ResponseEntity<Pregunta> updatePregunta(@PathVariable Long id, @RequestBody Pregunta nuevaPregunta) {
        return preguntaRepository.findById(id)
            .map(p -> {
                p.setTextoPregunta(nuevaPregunta.getTextoPregunta());
                p.setTipoPregunta(nuevaPregunta.getTipoPregunta());
                p.setExamen(nuevaPregunta.getExamen());
                return ResponseEntity.ok(preguntaRepository.save(p));
            }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una pregunta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePregunta(@PathVariable Long id) {
        if (preguntaRepository.existsById(id)) {
            preguntaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
