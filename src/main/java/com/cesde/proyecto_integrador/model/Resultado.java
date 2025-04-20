package com.cesde.proyecto_integrador.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "resultados")
@Data
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "La puntuacion no puede ser menor a 0")
    private double puntuacion;
    private LocalDateTime fechaInicio;

    @NotNull (message = "La fecha de finalizacion es obligatoria")
    private LocalDateTime fechaFinalizacion;

    // Relación con Examen
    @ManyToOne
    @JoinColumn(name = "examen_id")
    @NotNull (message = "El examen es obligatorio")
    private Examen examen;

    // Relación con User (estudiante que responde)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull (message = "El estudiante es obligatorio")
    private User estudiante; // Solo STUDENT
}

