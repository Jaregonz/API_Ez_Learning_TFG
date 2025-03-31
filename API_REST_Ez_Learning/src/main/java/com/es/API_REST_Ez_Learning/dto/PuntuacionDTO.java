package com.es.API_REST_Ez_Learning.dto;

public class PuntuacionDTO {
    private Long idUsuario;
    private Long idTest;
    private Integer puntuacion;

    public PuntuacionDTO(Long idUsuario, Long idTest, Integer puntuacion) {
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

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }
}
