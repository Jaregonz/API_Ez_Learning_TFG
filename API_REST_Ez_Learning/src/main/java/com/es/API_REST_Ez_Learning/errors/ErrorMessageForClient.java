package com.es.API_REST_Ez_Learning.errors;

public class ErrorMessageForClient {

    private String mensaje;
    private String uri;

    public ErrorMessageForClient(String mensaje, String uri){
        this.mensaje = mensaje;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
