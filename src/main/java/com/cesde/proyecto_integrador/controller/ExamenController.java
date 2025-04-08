package com.cesde.proyecto_integrador.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.service.ExamenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/examenes")
@Tag(name = "Examenes", description = "API para gestionar examenes")
public class ExamenController {

    @Autowired
    private ExamenService  examenService;

    @Operation(
        summary = "Obtener todos los exámenes",
        description = "Retorna una lista con todos los exámenes disponibles"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Lista de exámenes obtenida correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
    )

    @GetMapping
    public ResponseEntity<List<Examen>> getAllExams() {
        List<Examen> exams = examenService.findAll();
        return ResponseEntity.ok(exams);
    }

    @Operation(
        summary = "Crear un nuevo examen",
        description = "Crea un nuevo examen con la información proporcionada"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Examen creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
    )
    @PostMapping
    public ResponseEntity<Examen> createExamen(@RequestBody Examen examen) {
        Examen createdExamen = examenService.save(examen);
        return ResponseEntity.ok(createdExamen);
    }

    @Operation(
        summary = "Obtener un examen por ID del usuario ",
        description = "Retorna un examen específico basado en el ID proporcionado"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Examen encontrado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
    )
    @ApiResponse(
        responseCode = "404", 
        description = "Examen no encontrado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(
            @Parameter(description = "ID del examen a buscar") @PathVariable Long id) {
        Examen examen = examenService.findById(id);
        return ResponseEntity.ok(examen);
    }

    @Operation(
        summary = "Actualizar un examen",
        description = "Actualiza un examen existente con la información proporcionada"
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Examen actualizado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Examen.class))
    )
    @ApiResponse(
        responseCode = "404", 
        description = "Examen no encontrado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Examen> updateExamen(
            @Parameter(description = "ID del examen a actualizar") @PathVariable Long id, 
            @RequestBody Examen examenDetails) {
        Examen updatedExamen = examenService.update(id, examenDetails);
        return ResponseEntity.ok(updatedExamen);
    }

    @Operation(
        summary = "Eliminar un examen",
        description = "Elimina un examen basado en el ID proporcionado"
    )
    @ApiResponse(
        responseCode = "204", 
        description = "Examen eliminado correctamente"
    )
    @ApiResponse(
        responseCode = "404", 
        description = "Examen no encontrado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(
            @Parameter(description = "ID del examen a eliminar") @PathVariable Long id) {
        examenService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
