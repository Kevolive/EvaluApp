package com.cesde.proyecto_integrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesde.proyecto_integrador.dto.ResultadoRequestDTO;
import com.cesde.proyecto_integrador.model.Resultado;
import com.cesde.proyecto_integrador.service.ResultadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/resultados")
@Tag(name = "Resultados", description = "API para gestionar los resultados de los exámenes")
public class ResultadoController {

    @Autowired
    private ResultadoService resultadoService;

    @Operation(summary = "Crear resultados")
    @ApiResponse(responseCode = "200", description = "Resultados creados exitosamente")
    @PostMapping

    public ResponseEntity<Resultado> createResultado(@Valid @RequestBody ResultadoRequestDTO resultadoDTO) {
        return ResponseEntity.ok(resultadoService.save(resultadoDTO));
    }

    @Operation(summary = "Obtener todos resultado")
    @GetMapping 
    public ResponseEntity<List<Resultado>> getAllResultados() {
        return ResponseEntity.ok(resultadoService.findAll());
    }
    
    @Operation(summary = "Obtener resultado por ID")
    @GetMapping("/{id}") 
    public ResponseEntity<Resultado> getResultadoById(
        @Parameter(description = "ID del resultado a buscar") 
        @PathVariable Long id) {
        return ResponseEntity.ok(resultadoService.findById(id));
    }
    @Operation(summary = "Obtener resultado por ID")
    @GetMapping("/estudiante/{estudianteId}") 
    public ResponseEntity<List<Resultado>> getResultadoByEstudiante( 
        @PathVariable Long estudianteId) {
        return ResponseEntity.ok(resultadoService.findByEstudianteId(estudianteId));
    }
    
    
    
    

}
