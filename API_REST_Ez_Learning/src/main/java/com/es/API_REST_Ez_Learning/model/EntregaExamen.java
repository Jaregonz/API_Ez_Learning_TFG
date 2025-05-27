package com.es.API_REST_Ez_Learning.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "entregas_examenes")
public class EntregaExamen {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Usuario alumno;

    @ManyToOne(optional = false)
    private Examen examen;

    private String archivoRespuestaRuta;

    private String comentario;

    private Boolean aprobado;

    private LocalDate fechaEntrega;

    public EntregaExamen() {
    }

    public EntregaExamen(Usuario alumno, Examen examen, String archivoRespuestaRuta, String comentario, Boolean aprobado, LocalDate fechaEntrega) {
        this.alumno = alumno;
        this.examen = examen;
        this.archivoRespuestaRuta = archivoRespuestaRuta;
        this.comentario = comentario;
        this.aprobado = aprobado;
        this.fechaEntrega = fechaEntrega;
    }

    public Usuario getAlumno() {
        return alumno;
    }

    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public String getArchivoRespuestaRuta() {
        return archivoRespuestaRuta;
    }

    public void setArchivoRespuestaRuta(String archivoRespuestaRuta) {
        this.archivoRespuestaRuta = archivoRespuestaRuta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}