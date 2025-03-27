package com.es.API_REST_Ez_Learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaDTO {
    private Long preguntaId;
    private Long id;
    private String contenido;
    private boolean esCorrecta;

    public RespuestaDTO(Long id, String contenido, boolean esCorrecta) {
        this.id = id;
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    public RespuestaDTO(String contenido, boolean esCorrecta) {
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    public RespuestaDTO(Long preguntaId, Long id, String contenido, boolean esCorrecta) {
        this.preguntaId = preguntaId;
        this.id = id;
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    public RespuestaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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
