package com.cesde.proyecto_integrador.dto;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class ExamenRequestDTO {

    @Schema(description="Titulo del examen", example="Evaluacion final de java")
    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @Schema(description="Descripcion del examen", example="En este examen se evaluara tu conocimiento sobre while ")
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @Schema(description="Fecha de inicio del examen", example="2025-04-12")
     @NotNull(message = ("La fecha de inio es requerida"))
     @FutureOrPresent(message = "La fecha debe ser actual")
     private LocalDate fechaInicio;
 
    @Schema(description="Fecha  final del examen", example="2025-04-12")
     @NotNull(message = ("La fecha final es requerida"))
     @FutureOrPresent(message = "La fecha debe ser actual")
     private LocalDate fechaFin;
 
     @Schema(description="id del usuario que crea el examen(debe ser un profesor)", example="2")
     @NotNull(message = ("El id del creador es obligatorio"))
     private Long creadorId;
}
