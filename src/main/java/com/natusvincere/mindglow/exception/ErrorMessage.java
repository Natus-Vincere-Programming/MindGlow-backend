package com.natusvincere.mindglow.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
public class ErrorMessage {
    private final String message;
    private final int status;
}
