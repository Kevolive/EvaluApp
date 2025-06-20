package com.cesde.proyecto_integrador.controller;

import com.cesde.proyecto_integrador.dto.OpcionDTO;
import com.cesde.proyecto_integrador.dto.OpcionRequestDTO;
import com.cesde.proyecto_integrador.exception.ResourceNotFoundException;
import com.cesde.proyecto_integrador.model.Opcion;
import com.cesde.proyecto_integrador.model.Pregunta;
import com.cesde.proyecto_integrador.repository.OpcionRepository;
import com.cesde.proyecto_integrador.repository.PreguntaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/opciones")
@Tag(name = "Opciones", description = "API para gestionar opciones de preguntas")
public class OpcionController {

    private static final Logger log = LoggerFactory.getLogger(OpcionController.class);

    @Autowired
    private OpcionRepository opcionRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Operation(summary = "Crear una nueva opción")
    @ApiResponse(responseCode = "201", description = "Opción creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Opcion.class)))
    @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<Opcion> createOpcion(@Valid @RequestBody OpcionRequestDTO dto) {
        try {
            // Validar y obtener la pregunta
            Pregunta pregunta = preguntaRepository.findById(dto.getPreguntaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No existe una pregunta con el ID: " + dto.getPreguntaId()
                    ));

            // Verificar si ya existe una opción con el mismo texto para esta pregunta
            if (opcionRepository.existsByTextoOpcionAndPreguntaId(dto.getTextoOpcion(), dto.getPreguntaId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe una opción con el mismo texto para esta pregunta"
                );
            }

            // Mapear DTO a Entidad
            Opcion opcion = new Opcion();
            opcion.setTextoOpcion(dto.getTextoOpcion());
            opcion.setEsCorrecta(dto.getEsCorrecta());
            opcion.setPregunta(pregunta);

            // Guardar y retornar
            Opcion savedOpcion = opcionRepository.save(opcion);
            log.info("Opción creada exitosamente: {}", savedOpcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOpcion);
        } catch (Exception e) {
            log.error("Error al crear la opción: {}", e.getMessage(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al crear la opción: " + e.getMessage()
            );
        }
    }

    @Operation(summary = "Obtener opciones por pregunta")
    @ApiResponse(responseCode = "200", description = "Lista de opciones encontradas",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = OpcionDTO.class)))
    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<List<OpcionDTO>> getOpcionesByPregunta(@PathVariable Long preguntaId) {
        try {
            log.info("Buscando opciones para la pregunta con ID: {}", preguntaId);
            
            // Verificar si existe la pregunta
            if (!preguntaRepository.existsById(preguntaId)) {
                log.warn("No existe una pregunta con el ID: {}", preguntaId);
                throw new ResourceNotFoundException(
                        "No existe una pregunta con el ID: " + preguntaId
                );
            }

            // Obtener las opciones
            List<Opcion> opciones = opcionRepository.findByPreguntaId(preguntaId);
            log.info("Opciones encontradas: {}", opciones.size());
            
            if (opciones.isEmpty()) {
                log.warn("No se encontraron opciones para la pregunta con ID: {}", preguntaId);
                return ResponseEntity.ok(Collections.emptyList());
            }

            // Mapear a DTO
            List<OpcionDTO> opcionesDTO = opciones.stream()
                .map(opcion -> {
                    OpcionDTO dto = new OpcionDTO();
                    dto.setId(opcion.getId());
                    dto.setTextoOpcion(opcion.getTextoOpcion());
                    dto.setEsCorrecta(opcion.isEsCorrecta());
                    dto.setPreguntaId(preguntaId);
                    return dto;
                })
                .toList();
            
            log.info("Opciones mapeadas exitosamente: {}", opcionesDTO.size());
            return ResponseEntity.ok(opcionesDTO);
        } catch (Exception e) {
            log.error("Error al obtener opciones para la pregunta {}: {}", preguntaId, e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener las opciones: " + e.getMessage()
            );  
        }
    }
}