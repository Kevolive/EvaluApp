package com.cesde.proyecto_integrador.controller;

import com.cesde.proyecto_integrador.dto.ExamenDTO;
import com.cesde.proyecto_integrador.dto.ExamenRequestDTO;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.model.Pregunta;
import com.cesde.proyecto_integrador.model.Profile;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.repository.PreguntaRepository;
import com.cesde.proyecto_integrador.repository.UserRepository;
import com.cesde.proyecto_integrador.service.ExamenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examenes")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Examenes", description = "API para gestionar examenes")
public class ExamenController {

    private static final Logger log = LoggerFactory.getLogger(ExamenController.class);

    @Autowired
    private ExamenService examenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreguntaRepository PreguntaRepository;

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
                        Profile profile = examen.getCreador().getProfile();
                        dto.setCreadorNombre(profile != null ? profile.getName() : "Sin perfil");
                    } else {
                        dto.setCreadorId(null);
                        dto.setCreadorNombre("Desconocido");
                    }

                    if (examen.getPreguntas() != null && !examen.getPreguntas().isEmpty()) {
                        dto.setPreguntasIds(
                            examen.getPreguntas().stream()
                                .filter(Objects::nonNull)
                                .map(Pregunta::getId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                        );
                    } else {
                        dto.setPreguntasIds(Collections.emptyList());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(examDTOs);
        } catch (Exception e) {
            log.error("Error al obtener los exámenes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @Operation(summary = "Crear un nuevo examen")
    @PostMapping
    public ResponseEntity<ExamenDTO> createExamen(@Valid @RequestBody ExamenRequestDTO dto) {
        try {
            User creador = userRepository.findById(dto.getCreadorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + dto.getCreadorId()));

            Examen examen = new Examen();
            examen.setTitulo(dto.getTitulo());
            examen.setDescripcion(dto.getDescripcion());
            examen.setFechaInicio(dto.getFechaInicio());
            examen.setFechaFin(dto.getFechaFin());
            examen.setCreador(creador);

            Examen examenGuardado = examenService.save(examen);

            ExamenDTO response = new ExamenDTO();
            response.setId(examenGuardado.getId());
            response.setTitulo(examenGuardado.getTitulo());
            response.setDescripcion(examenGuardado.getDescripcion());
            response.setFechaInicio(examenGuardado.getFechaInicio());
            response.setFechaFin(examenGuardado.getFechaFin());
            response.setCreadorId(creador.getId());
            response.setCreadorNombre(creador.getProfile() != null ? creador.getProfile().getName() : "Sin perfil");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear el examen", e);
        }
    }

    @PostMapping("/{id}/preguntas")
    public ResponseEntity<String> asociarPreguntas(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            Examen examen = examenService.findById(id);
            if (examen == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Examen no encontrado");
            }

            List<?> ids = (List<?>) payload.get("preguntas");

            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista de preguntas no puede estar vacía");
            }

            List<Long> preguntasIds = ids.stream()
                .map(rawId -> rawId instanceof Number ? ((Number) rawId).longValue() : Long.valueOf(rawId.toString()))
                .collect(Collectors.toList());

            List<Pregunta> preguntas = PreguntaRepository.findAllById(preguntasIds);
            examen.setPreguntas(preguntas);
            examenService.save(examen);

            return ResponseEntity.ok("Preguntas asociadas correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asociar preguntas: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener preguntas de un examen")
    @GetMapping("/{id}/preguntas")
    public ResponseEntity<List<Pregunta>> getPreguntasByExamen(@PathVariable Long id) {
        List<Pregunta> preguntas = PreguntaRepository.findByExamenId(id);
        return ResponseEntity.ok(preguntas);
    }

    @Operation(summary = "Obtener un examen por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(@PathVariable Long id) {
        Examen examen = examenService.findById(id);
        return ResponseEntity.ok(examen);
    }

    @Operation(summary = "Actualizar un examen")
    @PutMapping("/{id}")
    public ResponseEntity<Examen> updateExamen(@PathVariable Long id, @RequestBody Examen examenDetails) {
        Examen updatedExamen = examenService.update(id, examenDetails);
        return ResponseEntity.ok(updatedExamen);
    }

    @Operation(summary = "Eliminar un examen")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        examenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
