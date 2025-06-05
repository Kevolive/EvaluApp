package com.cesde.proyecto_integrador.controller;

import com.cesde.proyecto_integrador.model.Pregunta;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.dto.PreguntaRequestDTO;
import com.cesde.proyecto_integrador.dto.PreguntaDTO; // ¡Importa tu DTO de salida!
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
import java.util.stream.Collectors; // Necesario para el stream

@RestController
@RequestMapping("/api/preguntas")
@Tag(name = "Preguntas", description = "API para gestionar preguntas de los exámenes")
public class PreguntaController {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private ExamenRepository examenRepository;

    // --- MÉTODOS GET MODIFICADOS PARA USAR PreguntaDTO ---

    @Operation(summary = "Obtener todas las preguntas")
    @GetMapping
    public ResponseEntity<List<PreguntaDTO>> getAllPreguntas() {
        // 1. Obtiene todas las entidades Pregunta de la base de datos
        List<Pregunta> preguntas = preguntaRepository.findAll();
        // 2. Mapea cada entidad Pregunta a un PreguntaDTO
        List<PreguntaDTO> preguntasDTO = preguntas.stream()
                .map(this::convertToDto) // Usa un método auxiliar para el mapeo
                .collect(Collectors.toList());
        // 3. Devuelve la lista de PreguntaDTOs
        return ResponseEntity.ok(preguntasDTO);
    }

    @Operation(summary = "Obtener una pregunta por ID")
    @ApiResponse(responseCode = "200", description = "Pregunta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PreguntaDTO.class))) // Actualiza el schema de Swagger
    @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO> getPreguntaById(@PathVariable Long id) {
        // 1. Obtiene la entidad Pregunta por ID
        Optional<Pregunta> pregunta = preguntaRepository.findById(id);
        // 2. Si la encuentra, la mapea a PreguntaDTO y la devuelve
        return pregunta.map(this::convertToDto) // Usa el mismo método auxiliar
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- MÉTODOS AUXILIARES PARA MAPEO (pueden ir en un Service si lo creas) ---
    private PreguntaDTO convertToDto(Pregunta pregunta) {
        PreguntaDTO dto = new PreguntaDTO();
        dto.setId(pregunta.getId());
        dto.setTextoPregunta(pregunta.getTextoPregunta());
        // Asumiendo que TipoPregunta es un Enum, lo conviertes a String
        if (pregunta.getTipoPregunta() != null) {
            dto.setTipoPregunta(pregunta.getTipoPregunta().name());
        }
        // Accede al examen y solo toma su ID
        if (pregunta.getExamen() != null) {
            dto.setExamenId(pregunta.getExamen().getId());
        }
        // Si tu entidad Pregunta tiene una relación con Opciones y quieres sus IDs:
        // if (pregunta.getOpciones() != null) {
        //     dto.setOpcionesIds(pregunta.getOpciones().stream()
        //                             .map(Opcion::getId) // 'Opcion' sería tu entidad de opciones
        //                             .collect(Collectors.toList()));
        // }
        return dto;
    }

    // --- LOS DEMÁS MÉTODOS (POST, PUT, DELETE) PUEDEN QUEDAR CASI IGUAL ---

    @Operation(summary = "Crear una nueva pregunta")
    @PostMapping
    public ResponseEntity<Pregunta> createPregunta(@Valid @RequestBody PreguntaRequestDTO dto) {
        Examen examen = examenRepository.findById(dto.getExamenId())
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con ID: " + dto.getExamenId()));

        Pregunta pregunta = new Pregunta();
        pregunta.setTextoPregunta(dto.getTextoPregunta());
        pregunta.setTipoPregunta(dto.getTipoPregunta());
        pregunta.setExamen(examen);
        // Aquí puedes setear los puntos si los tienes en la entidad Pregunta
        // pregunta.setPuntos(dto.getPuntos()); 

        // Ojo: Aquí sigues devolviendo la entidad Pregunta. 
        // Si quieres devolver un DTO después de crear, también tendrías que mapearlo.
        return ResponseEntity.ok(preguntaRepository.save(pregunta));
    }

    @Operation(summary = "Actualizar una pregunta existente")
    @PutMapping("/{id}")
    public ResponseEntity<Pregunta> updatePregunta(@PathVariable Long id, @RequestBody Pregunta nuevaPregunta) {
        // Aquí también estás esperando una entidad Pregunta en el @RequestBody
        // y devolviendo una entidad Pregunta.
        // Si quieres usar DTOs para actualizar, deberías usar PreguntaRequestDTO aquí también
        // y mapear el DTO a la entidad.
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