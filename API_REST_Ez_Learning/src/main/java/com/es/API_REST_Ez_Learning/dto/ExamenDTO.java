package com.es.API_REST_Ez_Learning.dto;

import java.time.LocalDate;

public class ExamenDTO {
    private Long id;
    private String titulo;
    private String archivoRuta;
    private LocalDate fechaCierre;

    public ExamenDTO() {
    }

    public ExamenDTO(Long id, String titulo, String archivoRuta, LocalDate fechaCierre) {
        this.id = id;
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
}
