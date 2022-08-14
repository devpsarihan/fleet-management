package com.fleetmanagement.application.exception;

import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.application.error.ApplicationError;
import com.fleetmanagement.infrastructure.exception.CoreException;
import lombok.Getter;

@Getter
public class ApplicationException extends CoreException {

    public ApplicationException(final ExceptionState state, final String code, final String message) {
        super(state, code, message);
    }

    public ApplicationException(final ExceptionState state, final ApplicationError applicationError) {
        this(state, applicationError.getCode(), applicationError.getMessage());
    }
}
