package com.cesde.proyecto_integrador.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User creador;  // Solo TEACHER

    // Relación bidireccional con Pregunta
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
