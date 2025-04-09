package com.es.API_REST_Ez_Learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaDTO {
    private String contenido;
    private boolean esCorrecta;


    public RespuestaDTO(String contenido, boolean esCorrecta) {
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    public RespuestaDTO() {
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

}
