package com.es.API_REST_Ez_Learning.errors;

public class ValidationException extends RuntimeException{
    private static final String DESCRIPCION = "BAD REQUEST (400)";

    public ValidationException(String mensaje){
        super(DESCRIPCION+". "+mensaje);
    }
}
