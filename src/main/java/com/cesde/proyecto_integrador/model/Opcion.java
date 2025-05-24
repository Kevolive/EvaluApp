package com.cesde.proyecto_integrador.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id")
    @JsonIgnore
    private Pregunta pregunta;

    
}