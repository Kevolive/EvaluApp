package com.cesde.proyecto_integrador.controller;

import com.cesde.proyecto_integrador.dto.OpcionRequestDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/opciones")
@Tag(name = "Opciones", description = "API para gestionar opciones de preguntas")
public class OpcionController {

    @Autowired
    private OpcionRepository opcionRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Operation(summary = "Crear una nueva opción")
    @ApiResponse(responseCode = "201", description = "Opción creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Opcion.class)))
    @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    @PostMapping
    public ResponseEntity<Opcion> createOpcion(@Valid @RequestBody OpcionRequestDTO dto) {
        // Validar y obtener la pregunta
        Pregunta pregunta = preguntaRepository.findById(dto.getPreguntaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe una pregunta con el ID: " + dto.getPreguntaId()
                ));

        // Mapear DTO a Entidad
        Opcion opcion = new Opcion();
        opcion.setTextoOpcion(dto.getTextoOpcion());
        opcion.setEsCorrecta(dto.getEsCorrecta()); // Usamos Boolean del DTO
        opcion.setPregunta(pregunta);

        // Guardar y retornar
        Opcion savedOpcion = opcionRepository.save(opcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOpcion);
    }

    @Operation(summary = "Obtener opciones por pregunta")
    @ApiResponse(responseCode = "200", description = "Lista de opciones encontradas",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Opcion.class)))
    @GetMapping("/pregunta/{preguntaId}")
    public ResponseEntity<List<Opcion>> getOpcionesByPregunta(@PathVariable Long preguntaId) {
        if (!preguntaRepository.existsById(preguntaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No existe una pregunta con el ID: " + preguntaId
            );
        }
        List<Opcion> opciones = opcionRepository.findByPreguntaId(preguntaId);
        return ResponseEntity.ok(opciones);
    }
}