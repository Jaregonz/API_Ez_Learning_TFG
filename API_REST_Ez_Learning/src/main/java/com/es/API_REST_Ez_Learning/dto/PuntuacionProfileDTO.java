package com.es.API_REST_Ez_Learning.dto;

public class PuntuacionProfileDTO {
    private Long idUsuario;
    private Long idTest;
    private String puntuacion;
    private String titulo;
    private String nivel;
    private String tipo;
    private int totalPreguntas;

    public PuntuacionProfileDTO(Long idUsuario, Long idTest, String puntuacion, String titulo, String nivel, String tipo, int totalPreguntas) {
        this.idUsuario = idUsuario;
        this.idTest = idTest;
        this.puntuacion = puntuacion;
        this.titulo = titulo;
        this.nivel = nivel;
        this.tipo = tipo;
        this.totalPreguntas = totalPreguntas;
    }

    public PuntuacionProfileDTO() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    public void setTotalPreguntas(int totalPreguntas) {
        this.totalPreguntas = totalPreguntas;
    }
}
