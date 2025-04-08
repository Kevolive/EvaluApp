package com.cesde.proyecto_integrador.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "opciones")
@Data
public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoOpcion;
    private boolean esCorrecta;

    // Relación con Pregunta
    @ManyToOne
    @JoinColumn(name = "pregunta_id")
    private Pregunta pregunta;
}