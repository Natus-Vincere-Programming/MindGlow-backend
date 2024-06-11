package com.natusvincere.mindglow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class HttpForbiddenException extends RuntimeException {
    public HttpForbiddenException(String message) {
        super(message);
    }
}
