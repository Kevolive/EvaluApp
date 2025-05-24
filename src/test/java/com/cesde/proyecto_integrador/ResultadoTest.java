package com.cesde.proyecto_integrador;

import com.cesde.proyecto_integrador.dto.ResultadoRequestDTO;
import com.cesde.proyecto_integrador.model.Examen;
import com.cesde.proyecto_integrador.model.Opcion;
import com.cesde.proyecto_integrador.model.Pregunta;
import com.cesde.proyecto_integrador.model.Resultado;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.model.Pregunta.TipoPregunta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultadoTest {

    private Examen examen;
    private User estudiante;
    private Resultado resultado;

    @BeforeEach
    public void setUp() {
        // Crear un examen de prueba
        examen = new Examen();
        examen.setId(1L);
        examen.setTitulo("Examen de Prueba");
        
        // Crear preguntas y opciones
        Pregunta pregunta1 = new Pregunta();
        pregunta1.setId(1L);
        pregunta1.setTextoPregunta("¿Qué es Java?");
        pregunta1.setTipoPregunta(TipoPregunta.SELECCION_UNICA);
        
        // Opciones para pregunta 1
        Opcion opcion1_1 = new Opcion();
        opcion1_1.setId(1L);
        opcion1_1.setTextoOpcion("Un lenguaje de programación");
        opcion1_1.setEsCorrecta(true);
        
        Opcion opcion1_2 = new Opcion();
        opcion1_2.setId(2L);
        opcion1_2.setTextoOpcion("Un tipo de café");
        opcion1_2.setEsCorrecta(false);
        
        pregunta1.setOpciones(Arrays.asList(opcion1_1, opcion1_2));
        
        Pregunta pregunta2 = new Pregunta();
        pregunta2.setId(2L);
        pregunta2.setTextoPregunta("¿Qué características tiene Java?");
        pregunta2.setTipoPregunta(TipoPregunta.MULTIPLE);
        
        // Opciones para pregunta 2
        Opcion opcion2_1 = new Opcion();
        opcion2_1.setId(3L);
        opcion2_1.setTextoOpcion("Es orientado a objetos");
        opcion2_1.setEsCorrecta(true);
        
        Opcion opcion2_2 = new Opcion();
        opcion2_2.setId(4L);
        opcion2_2.setTextoOpcion("Es de código abierto");
        opcion2_2.setEsCorrecta(true);
        
        Opcion opcion2_3 = new Opcion();
        opcion2_3.setId(5L);
        opcion2_3.setTextoOpcion("Es un framework");
        opcion2_3.setEsCorrecta(false);
        
        pregunta2.setOpciones(Arrays.asList(opcion2_1, opcion2_2, opcion2_3));
        
        // Asignar preguntas al examen
        examen.setPreguntas(Arrays.asList(pregunta1, pregunta2));
        
        // Crear estudiante
        estudiante = new User();
        estudiante.setId(1L);
        estudiante.setEmail("estudiante@test.com");
        
        // Crear resultado
        resultado = new Resultado();
        resultado.setUsuario(estudiante);
        resultado.setExamen(examen);
    }

    @Test
    public void testCalcularPuntajeRespuestasCorrectas() {
        // Respuestas correctas
        resultado.setOpcionesSeleccionadas(Arrays.asList(1L, 3L, 4L));
        resultado.calcularPuntaje();
        
        // Debería obtener 100% ya que todas las respuestas son correctas
        assertEquals(100, resultado.getPuntaje());
    }

    @Test
    public void testCalcularPuntajeRespuestasIncorrectas() {
        // Respuestas incorrectas
        resultado.setOpcionesSeleccionadas(Arrays.asList(2L, 3L, 5L));
        resultado.calcularPuntaje();
        
        // Debería obtener 50% ya que solo una respuesta es correcta
        assertEquals(50, resultado.getPuntaje());
    }

    @Test
    public void testCalcularPuntajeRespuestasParcialmenteCorrectas() {
        // Una respuesta correcta y otra incorrecta
        resultado.setOpcionesSeleccionadas(Arrays.asList(1L, 3L, 5L));
        resultado.calcularPuntaje();
        
        // Debería obtener 50% ya que una pregunta está bien y otra mal
        assertEquals(50, resultado.getPuntaje());
    }
}
