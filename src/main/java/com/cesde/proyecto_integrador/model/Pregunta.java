package com.cesde.proyecto_integrador.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "examen_id")
    private Examen examen;

    // Relación con Opcion
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcion> opciones;

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public enum TipoPregunta {
        SELECCION_UNICA,
        MULTIPLE,
        TEXTO_ABIERTO
    }
}
