package com.cesde.proyecto_integrador.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "examen")
@Data
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Relación con User (creador del examen)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User creador;  // Solo TEACHER

    // Relación bidireccional con Pregunta
    @ManyToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pregunta> preguntas;
    
}
