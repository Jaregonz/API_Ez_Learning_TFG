package com.es.API_REST_Ez_Learning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pregunta_id")
    private Pregunta pregunta;

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private boolean esCorrecta;

    public Respuesta() {
    }

    public Respuesta(Pregunta pregunta, String contenido, boolean esCorrecta) {
        this.pregunta = pregunta;
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    public Respuesta(Long id, Pregunta pregunta, String contenido, boolean esCorrecta) {
        this.id = id;
        this.pregunta = pregunta;
        this.contenido = contenido;
        this.esCorrecta = esCorrecta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
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