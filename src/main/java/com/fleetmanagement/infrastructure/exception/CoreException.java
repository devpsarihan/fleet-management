package com.fleetmanagement.infrastructure.exception;

import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import lombok.Getter;

@Getter
public abstract class CoreException extends RuntimeException {

    private final ExceptionState state;
    private final String code;
    private final String message;

    protected CoreException(final ExceptionState state, final String code, final String message) {
        super(message);
        this.state = state;
        this.code = code;
        this.message = message;
    }
}
