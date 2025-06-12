package com.cesde.proyecto_integrador.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "preguntas")
@Getter
@Setter
@ToString(exclude = { "examen", "opciones" })
@EqualsAndHashCode(exclude = { "examen", "opciones" })
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoPregunta;

    @Enumerated(EnumType.STRING)
    private TipoPregunta tipoPregunta;

    
    @Column(name = "puntos")
    private Integer puntos = 1;


    @ManyToOne
    @JoinColumn(name = "examen_id")
    @JsonIgnore
    private Examen examen;

    // ✅ Relación con Opcion
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcion> opciones;

    public enum TipoPregunta {
        SELECCION_UNICA,
        MULTIPLE,
        TEXTO_ABIERTO
    }
}
