package com.es.API_REST_Ez_Learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreguntaDTO {
    private Long id;
    private String contenidoPregunta;
    private List<RespuestaDTO> respuestas;

    public PreguntaDTO(Long id, String contenidoPregunta, List<RespuestaDTO> respuestas) {
        this.id = id;
        this.contenidoPregunta = contenidoPregunta;
        this.respuestas = respuestas;
    }

    public PreguntaDTO(String contenidoPregunta, List<RespuestaDTO> respuestas) {
        this.contenidoPregunta = contenidoPregunta;
        this.respuestas = respuestas;
    }

    public PreguntaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenidoPregunta() {
        return contenidoPregunta;
    }

    public void setContenidoPregunta(String contenidoPregunta) {
        this.contenidoPregunta = contenidoPregunta;
    }

    public List<RespuestaDTO> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaDTO> respuestas) {
        this.respuestas = respuestas;
    }
}

