package com.es.API_REST_Ez_Learning.dto;

public class EntregaFeedbackDTO {
    private String comentario;
    private Boolean aprobado;

    public EntregaFeedbackDTO() {
    }
    public EntregaFeedbackDTO(String comentario, Boolean aprobado) {
        this.comentario = comentario;
        this.aprobado = aprobado;
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
}
