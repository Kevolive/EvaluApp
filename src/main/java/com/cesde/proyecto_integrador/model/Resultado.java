package com.cesde.proyecto_integrador.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resultados")
@Data
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double puntuacion;
    private LocalDateTime fechaFinalizacion;

    // Relación con Examen
    @ManyToOne
    @JoinColumn(name = "examen_id")
    private Examen examen;

    // Relación con User (estudiante que responde)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User estudiante; // Solo STUDENT
}

