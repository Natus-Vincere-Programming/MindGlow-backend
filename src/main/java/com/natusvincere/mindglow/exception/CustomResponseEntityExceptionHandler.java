package com.natusvincere.mindglow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProcessingException.class)
    public final ResponseEntity<?> handleProcessingException(ProcessingException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(value = AccessException.class)
    public final ResponseEntity<?> handleAccessException(AccessException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public final ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
