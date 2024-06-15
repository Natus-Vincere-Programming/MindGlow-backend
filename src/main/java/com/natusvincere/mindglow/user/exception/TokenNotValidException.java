package com.natusvincere.mindglow.user.exception;

import com.natusvincere.mindglow.exception.AccessException;

public class TokenNotValidException extends AccessException {
    public TokenNotValidException(String message) {
        super(message);
    }
}
