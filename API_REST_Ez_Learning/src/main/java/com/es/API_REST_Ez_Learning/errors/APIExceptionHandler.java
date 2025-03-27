package com.es.API_REST_Ez_Learning.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageForClient handleBadRequest(HttpServletRequest request, Exception e){
       return new ErrorMessageForClient(e.getMessage(),request.getRequestURI());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageForClient handleResourceNotFound(HttpServletRequest request, Exception e){
        return new ErrorMessageForClient(e.getMessage(),request.getRequestURI());
    }


    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessageForClient handleConflictPreguntas(HttpServletRequest request, Exception e){
        return new ErrorMessageForClient(e.getMessage(),request.getRequestURI());
    }

    @ExceptionHandler({InternalServerError.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessageForClient handleInternalServerError(HttpServletRequest request, Exception e){
        return new ErrorMessageForClient(e.getMessage(),request.getRequestURI());
    }

    @ExceptionHandler({NotAuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorMessageForClient handleNotAuthorizationException(HttpServletRequest request, Exception e){
        return new ErrorMessageForClient(e.getMessage(),request.getRequestURI());
    }
}
