package com.cesde.proyecto_integrador.dto;

import com.cesde.proyecto_integrador.model.Pregunta.TipoPregunta;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PreguntaRequestDTO {

    @Schema(description = "Texto de la pregunta", example = "¿Qué es Java?")
    @NotBlank(message = "El texto de la pregunta no puede estar vacío")
    @Size(min = 5, message = "La pregunta debe tener al menos 5 caracteres")
    private String textoPregunta;

    @Schema(description = "Tipo de la pregunta", example = "SELECCION_UNICA")
    @NotNull(message = "El tipo de pregunta es obligatorio")
    private TipoPregunta tipoPregunta;

    @Schema(description = "ID del examen al que pertenece", example = "3")
    @NotNull(message = "El ID del examen es obligatorio")
    private Long examenId;

    @Schema(description = "Puntaje que vale esta pregunta", example = "5")
    @Min(value = 1, message = "Los puntos deben ser al menos 1")
    private int puntos = 1;
}
