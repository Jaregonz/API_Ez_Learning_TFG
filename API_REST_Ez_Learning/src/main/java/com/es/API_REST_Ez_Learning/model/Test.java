package com.es.API_REST_Ez_Learning.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_creador_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, length = 20)
    private String dificultad;

    @Column(nullable = false, name = "cantidad_preguntas")
    private int cantidadPreguntas;

    @Column(nullable = false)
    private int tiempo;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tests_preguntas",
            joinColumns = @JoinColumn(name = "id_test"),
            inverseJoinColumns = @JoinColumn(name = "id_pregunta"))
    private List<Pregunta> preguntas;

    public Test(Usuario usuario, String tipo, String titulo,String dificultad, int cantidadPreguntas, int tiempo, List<Pregunta> preguntas) {
        this.usuario = usuario;
        this.dificultad = dificultad;
        this.tipo = tipo;
        this.titulo = titulo;
        this.cantidadPreguntas = cantidadPreguntas;
        this.tiempo = tiempo;
        this.preguntas = preguntas;
    }

    public Test() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
