package com.cesde.proyecto_integrador.controller;

import com.cesde.proyecto_integrador.dto.ExamenDTO;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.model.Profile;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.repository.UserRepository;
import com.cesde.proyecto_integrador.service.ExamenService;
import com.cesde.proyecto_integrador.repository.PreguntaRepository;
import com.cesde.proyecto_integrador.model.Pregunta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import java.util.Collections;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cesde.proyecto_integrador.dto.ExamenRequestDTO;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@Tag(name = "Examenes", description = "API para gestionar examenes")
public class ExamenController {

    private static final Logger log = LoggerFactory.getLogger(ExamenController.class);

    @Autowired
    private ExamenService examenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreguntaRepository PreguntaRepository;

    @Operation(summary = "Obtener preguntas de un examen", description = "Retorna todas las preguntas asociadas a un examen")
    @ApiResponse(responseCode = "200", description = "Preguntas obtenidas correctamente", content = @Content(mediaType = "application/json"))
    @GetMapping("/{id}/preguntas")
    public ResponseEntity<List<Pregunta>> getPreguntasByExamen(@PathVariable Long id) {
        List<Pregunta> preguntas = PreguntaRepository.findByExamenId(id);
        return ResponseEntity.ok(preguntas);
    }

    @Operation(summary = "Obtener todos los exámenes", description = "Retorna una lista con todos los exámenes disponibles en formato DTO")
    @ApiResponse(responseCode = "200", description = "Lista de exámenes obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExamenDTO.class)))
    @GetMapping
    public ResponseEntity<List<ExamenDTO>> getAllExams() {
        try {
            List<Examen> exams = examenService.findAll();
            List<ExamenDTO> examDTOs = exams.stream()
                    .map(examen -> {
                        ExamenDTO dto = new ExamenDTO();
                        dto.setId(examen.getId());
                        dto.setTitulo(examen.getTitulo());
                        dto.setDescripcion(examen.getDescripcion());
                        dto.setFechaInicio(examen.getFechaInicio());
                        dto.setFechaFin(examen.getFechaFin());
                        if (examen.getCreador() != null) {
                            dto.setCreadorId(examen.getCreador().getId());
                            // Usar el nombre del perfil del usuario si existe
                            Profile profile = examen.getCreador().getProfile();
                            if (profile != null) {
                                dto.setCreadorNombre(profile.getName());
                            } else {
                                dto.setCreadorNombre("Sin perfil");
                            }
                        }
                        if (examen.getPreguntas() != null) {
                            dto.setPreguntasIds(examen.getPreguntas().stream()
                                    .map(pregunta -> pregunta.getId())
                                    .toList());
                        }
                        return dto;
                    })
                    .toList();
            return ResponseEntity.ok(examDTOs);
        } catch (Exception e) {
            log.error("Error al obtener los exámenes: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @Operation(summary = "Crear un nuevo examen", description = "Crea un nuevo examen con la información proporcionada")
    @ApiResponse(responseCode = "200", description = "Examen creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class)))
    @PostMapping
    public ResponseEntity<Examen> createExamen(@Valid @RequestBody ExamenRequestDTO dto) {

        User creador = userRepository.findById(dto.getCreadorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getCreadorId()));

        Examen examen = new Examen();
        examen.setTitulo(dto.getTitulo());
        examen.setDescripcion(dto.getDescripcion());
        examen.setFechaInicio(dto.getFechaInicio());
        examen.setFechaFin(dto.getFechaFin());
        examen.setCreador(creador);
        return ResponseEntity.ok(examenService.save(examen));
    }

    @Operation(summary = "Obtener un examen por ID del usuario ", description = "Retorna un examen específico basado en el ID proporcionado")
    @ApiResponse(responseCode = "200", description = "Examen encontrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class)))
    @ApiResponse(responseCode = "404", description = "Examen no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(
            @Parameter(description = "ID del examen a buscar") @PathVariable Long id) {
        Examen examen = examenService.findById(id);
        return ResponseEntity.ok(examen);
    }

    @Operation(summary = "Actualizar un examen", description = "Actualiza un examen existente con la información proporcionada")
    @ApiResponse(responseCode = "200", description = "Examen actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class)))
    @ApiResponse(responseCode = "404", description = "Examen no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Examen> updateExamen(
            @Parameter(description = "ID del examen a actualizar") @PathVariable Long id,
            @RequestBody Examen examenDetails) {
        Examen updatedExamen = examenService.update(id, examenDetails);
        return ResponseEntity.ok(updatedExamen);
    }

    @Operation(summary = "Eliminar un examen", description = "Elimina un examen basado en el ID proporcionado")
    @ApiResponse(responseCode = "204", description = "Examen eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Examen no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(
            @Parameter(description = "ID del examen a eliminar") @PathVariable Long id) {
        examenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
