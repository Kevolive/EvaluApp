package com.cesde.proyecto_integrador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para recibir datos de una opción de respuesta")
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

    // --- Getters y Setters ---
    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }

    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    @Override
    public String toString() {
        return "OpcionRequestDTO{" +
                "textoOpcion='" + textoOpcion + '\'' +
                ", esCorrecta=" + esCorrecta +
                ", preguntaId=" + preguntaId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpcionRequestDTO)) return false;

        OpcionRequestDTO that = (OpcionRequestDTO) o;

        if (!textoOpcion.equals(that.textoOpcion)) return false;
        if (!esCorrecta.equals(that.esCorrecta)) return false;
        return preguntaId.equals(that.preguntaId);
    }

    @Override
    public int hashCode() {
        int result = textoOpcion.hashCode();
        result = 31 * result + esCorrecta.hashCode();
        result = 31 * result + preguntaId.hashCode();
        return result;
    }
}
