package com.fleetmanagement.domain.exception;

import com.fleetmanagement.domain.error.DomainError;
import com.fleetmanagement.infrastructure.exception.CoreException;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import lombok.Getter;

@Getter
public class DomainException extends CoreException {

    public DomainException(final ExceptionState state, final String code, final String message) {
        super(state, code, message);
    }

    public DomainException(final ExceptionState state, final DomainError domainError) {
        this(state, domainError.getCode(), domainError.getMessage());
    }
}
