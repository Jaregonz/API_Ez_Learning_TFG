package com.es.API_REST_Ez_Learning.dto;

public class EntregaDTO {
    private Long id;
    private Long examenId;
    private Long idAlumno;
    private String alumnoNombre;
    private String archivoRespuestaRuta;
    private String comentario;
    private Boolean aprobado;

    public EntregaDTO() {
    }

    public EntregaDTO(Long id, String alumnoNombre, String archivoRespuestaRuta, String comentario, Boolean aprobado, Long examenId, Long idAlumno) {
        this.id = id;
        this.alumnoNombre = alumnoNombre;
        this.archivoRespuestaRuta = archivoRespuestaRuta;
        this.comentario = comentario;
        this.aprobado = aprobado;
        this.examenId = examenId;
        this.idAlumno = idAlumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlumnoNombre() {
        return alumnoNombre;
    }

    public void setAlumnoNombre(String alumnoNombre) {
        this.alumnoNombre = alumnoNombre;
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

    public Long getExamenId() {
        return examenId;
    }

    public void setExamenId(Long examenId) {
        this.examenId = examenId;
    }
    public Long getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }
}