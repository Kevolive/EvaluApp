package com.cesde.proyecto_integrador.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ExamenDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long creadorId;
    private String creadorNombre;
    private List<Long> preguntasIds;
}
