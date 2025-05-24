package com.cesde.proyecto_integrador.dto;

public class OpcionDTO {
    private Long id;
    private String textoOpcion;
    private boolean esCorrecta;
    private Long preguntaId;

    public OpcionDTO() {
        // Constructor vacío requerido para deserialización
    }

    public OpcionDTO(Long id, String textoOpcion, boolean esCorrecta) {
        this.id = id;
        this.textoOpcion = textoOpcion;
        this.esCorrecta = esCorrecta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }
}
