package com.wup.currencyconverter.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Change default exception handling to reveal inner details of the application via REST API
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle validation errors: display a short unified message for each field/object
     */
    @ExceptionHandler(value = { BindException.class })
    public ResponseEntity bindException(BindException ex, WebRequest req) {
        ex.printStackTrace();

        List<String> errors = ex.getAllErrors().stream().map(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return String.format("%s.%s: %s", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
            }

            return String.format("%s: %s", objectError.getObjectName(), objectError.getDefaultMessage());
        }).collect(Collectors.toList());

        return new ResponseEntity(errors, null, HttpStatus.BAD_REQUEST);
    }

    /**
     * In case of all other errors, respond with internal server error, without revealing other details
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity otherExceptions(Exception ex, WebRequest req){
        ex.printStackTrace();

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}