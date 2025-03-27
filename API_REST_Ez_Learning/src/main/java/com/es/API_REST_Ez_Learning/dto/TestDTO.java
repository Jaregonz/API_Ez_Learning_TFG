package com.es.API_REST_Ez_Learning.dto;

import com.es.API_REST_Ez_Learning.model.Pregunta;
import com.es.API_REST_Ez_Learning.model.Usuario;

import java.util.List;

public class TestDTO {
    private Long usuarioId;
    private String tipo;
    private String titulo;
    private int cantidadPreguntas;
    private int tiempo;
    private List<PreguntaDTO> preguntas;
    private String dificultad;

    public TestDTO(Long usuarioId, String tipo, String titulo, String dificultad, int cantidadPreguntas, int tiempo, List<PreguntaDTO> preguntas) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.titulo = titulo;
        this.dificultad = dificultad;
        this.cantidadPreguntas = cantidadPreguntas;
        this.tiempo = tiempo;
        this.preguntas = preguntas;
    }

    public TestDTO() {
    }

    public Long getUsuario() {
        return usuarioId;
    }

    public void setUsuario(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadPreguntas() {
        return cantidadPreguntas;
    }

    public void setCantidadPreguntas(int cantidadPreguntas) {
        this.cantidadPreguntas = cantidadPreguntas;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public List<PreguntaDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaDTO> preguntas) {
        this.preguntas = preguntas;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
