package com.es.API_REST_Ez_Learning.errors;

public class ResourceNotFoundException extends RuntimeException{
    private static final String DESCRIPCION = "NOT FOUND (404)";
    public ResourceNotFoundException(String mensaje){
        super(DESCRIPCION+". "+mensaje);
    }
}
