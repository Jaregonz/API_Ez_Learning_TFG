package com.es.API_REST_Ez_Learning.errors;

public class NotAuthorizationException extends RuntimeException{
    private static final String DESCRIPCION = "NOT AUTHORIZED 401";
    public NotAuthorizationException(String mensaje){
        super(DESCRIPCION+". "+mensaje);
    }
}
