package com.cesde.proyecto_integrador.dto;

import lombok.Data;
import java.util.List;

@Data
public class PreguntaDTO {
    private Long id;
    private String textoPregunta;
    private String tipoPregunta;
    private Long examenId;
    private List<Long> opcionesIds;
}
