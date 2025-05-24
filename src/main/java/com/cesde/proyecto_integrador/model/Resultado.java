package com.cesde.proyecto_integrador.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "resultados")
@Data
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer puntaje;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "examen_id")
    private Examen examen;

    @ElementCollection
    @CollectionTable(name = "opciones_seleccionadas",
        joinColumns = @JoinColumn(name = "resultado_id"))
    @Column(name = "opcion_id")
    private List<Long> opcionesSeleccionadas; // IDs de las opciones elegidas

    /**
     * Calcula el puntaje del resultado basado en las opciones seleccionadas
     */
    public void calcularPuntaje() {
        if (examen == null) {
            throw new IllegalStateException("El examen no puede ser nulo para calcular el puntaje");
        }

        int totalPuntos = 0;
        int puntosObtenidos = 0;

        // Obtener todas las preguntas del examen
        List<Pregunta> preguntas = examen.getPreguntas();
        
        for (Pregunta pregunta : preguntas) {
            // Obtener opciones de la pregunta
            List<Opcion> opciones = pregunta.getOpciones();
            
            // Obtener opciones correctas de la pregunta
            List<Long> opcionesCorrectas = opciones.stream()
                .filter(Opcion::isEsCorrecta)
                .map(Opcion::getId)
                .collect(Collectors.toList());

            // Verificar si las opciones seleccionadas coinciden con las correctas
            boolean respuestaCorrecta = false;
            
            // Para selección única, debe seleccionar exactamente una opción correcta
            if (pregunta.getTipoPregunta() == Pregunta.TipoPregunta.SELECCION_UNICA) {
                respuestaCorrecta = opcionesCorrectas.size() == 1 && 
                                  opcionesSeleccionadas != null &&
                                  opcionesSeleccionadas.size() == 1 &&
                                  opcionesSeleccionadas.containsAll(opcionesCorrectas);
            }
            // Para múltiple, debe seleccionar todas las opciones correctas
            else if (pregunta.getTipoPregunta() == Pregunta.TipoPregunta.MULTIPLE) {
                respuestaCorrecta = opcionesSeleccionadas != null &&
                                  opcionesSeleccionadas.containsAll(opcionesCorrectas) &&
                                  opcionesCorrectas.containsAll(opcionesSeleccionadas);
            }
            // Para texto abierto, no se calcula puntaje automático
            else if (pregunta.getTipoPregunta() == Pregunta.TipoPregunta.TEXTO_ABIERTO) {
                continue;
            }

            // Si la respuesta es correcta, se suma el puntaje
            if (respuestaCorrecta) {
                puntosObtenidos++;
            }
            
            totalPuntos++;
        }

        // Calcular el puntaje final (porcentaje)
        if (totalPuntos > 0) {
            this.puntaje = (int) ((puntosObtenidos / (double) totalPuntos) * 100);
        } else {
            this.puntaje = 0;
        }
    }
}
