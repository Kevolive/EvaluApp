package com.cesde.proyecto_integrador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OpcionRequestDTO {


    @Schema(description = "Texto visible de la opción", example = "Polimorfismo")
    @NotBlank(message = "El texto de la opción no puede estar vacío")
    private String textoOpcion;

    @Schema(description = "Indica si la opción es correcta", example = "true")
    @NotNull(message = "Debe indicar si la opción es correcta o no")
    private Boolean esCorrecta;

    @Schema(description = "ID de la pregunta a la que pertenece", example = "4")
    @NotNull(message = "Debe especificar el ID de la pregunta")
    private Long preguntaId;



}
