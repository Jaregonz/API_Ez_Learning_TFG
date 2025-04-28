package com.es.API_REST_Ez_Learning.dto;

public class PuntuacionDTO {
    private Long idUsuario;
    private Long idTest;
    private String puntuacion;

    public PuntuacionDTO(Long idUsuario, Long idTest, String puntuacion) {
        this.idUsuario = idUsuario;
        this.idTest = idTest;
        this.puntuacion = puntuacion;
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
}
