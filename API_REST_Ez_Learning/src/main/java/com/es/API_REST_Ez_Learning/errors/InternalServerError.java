package com.es.API_REST_Ez_Learning.errors;

public class InternalServerError extends RuntimeException{
    private static final String DESCRIPCION = "INTERNAL SERVER ERROR 500";
    public InternalServerError(String mensaje){
        super(DESCRIPCION+". "+mensaje);
    }
}
