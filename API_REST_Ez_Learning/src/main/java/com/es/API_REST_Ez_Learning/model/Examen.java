package com.es.API_REST_Ez_Learning.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "examenes")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario profesor;

    private String titulo;

    private String archivoRuta;

    private LocalDate fechaCierre;

    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL)
    private List<EntregaExamen> entregas = new ArrayList<>();

    public Examen() {
    }

    public Examen(Usuario profesor, String titulo, String archivoRuta, LocalDate fechaCierre) {
        this.profesor = profesor;
        this.titulo = titulo;
        this.archivoRuta = archivoRuta;
        this.fechaCierre = fechaCierre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArchivoRuta() {
        return archivoRuta;
    }

    public void setArchivoRuta(String archivoRuta) {
        this.archivoRuta = archivoRuta;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public List<EntregaExamen> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<EntregaExamen> entregas) {
        this.entregas = entregas;
    }
}
