package com.es.API_REST_Ez_Learning.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido_pregunta")
    private String contenidoPregunta;

    @OneToMany(mappedBy = "pregunta", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Respuesta> respuestas;

    public Pregunta() {
    }

    public Pregunta(String contenidoPregunta, List<Respuesta> respuestas) {
        this.contenidoPregunta = contenidoPregunta;
        this.respuestas = respuestas;
    }

    public Pregunta(Long id, String contenidoPregunta, List<Respuesta> respuestas) {
        this.id = id;
        this.contenidoPregunta = contenidoPregunta;
        this.respuestas = respuestas;
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

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}
