package com.es.API_REST_Ez_Learning.errors;

public class ConflictException extends RuntimeException{
    private static final String DESCRIPCION = "CONFLICT 409";
    public ConflictException(String mensaje){
        super(DESCRIPCION+". "+mensaje);
    }
}
