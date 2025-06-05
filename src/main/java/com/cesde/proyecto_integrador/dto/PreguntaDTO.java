package com.cesde.proyecto_integrador.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import com.cesde.proyecto_integrador.model.Pregunta;
import com.cesde.proyecto_integrador.model.Opcion;

@Data
@NoArgsConstructor
public class PreguntaDTO {
    private Long id;
    private String textoPregunta;
    private String tipoPregunta;
    private Long examenId;
    private List<Long> opcionesIds;

    // Constructor para mapeo desde entidad
    public PreguntaDTO(Pregunta pregunta) {
        this.id = pregunta.getId();
        this.textoPregunta = pregunta.getTextoPregunta();
        this.tipoPregunta = pregunta.getTipoPregunta() != null ? pregunta.getTipoPregunta().name() : null;
        this.examenId = pregunta.getExamen() != null ? pregunta.getExamen().getId() : null;
        this.opcionesIds = pregunta.getOpciones() != null ? 
            pregunta.getOpciones().stream()
                .map(Opcion::getId)
                .collect(Collectors.toList()) : null;
    }
}
