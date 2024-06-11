package com.natusvincere.mindglow.exception;

import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @ExceptionHandler(value = {HttpForbiddenException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {;
        return handleExceptionInternal(ex, new ErrorResponse(ex.getMessage()), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    private record ErrorResponse(String message) {
    }
}
