package com.cesde.proyecto_integrador.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "preguntas")
@Data
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoPregunta;

    @Enumerated(EnumType.STRING)
    private TipoPregunta tipoPregunta; // Ej: SELECCION_UNICA, MULTIPLE, TEXTO

    // Relación con Examen
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_id")
    private Examen examen;

    // Relación bidireccional con Opcion
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Opcion> opciones;

    public enum TipoPregunta {
        SELECCION_UNICA,
        MULTIPLE,
        TEXTO_ABIERTO
    }
}
