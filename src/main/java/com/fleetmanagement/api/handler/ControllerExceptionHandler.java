package com.fleetmanagement.api.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.PRECONDITION_REQUIRED;

import com.fleetmanagement.api.handler.error.ErrorContractBuilder;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.infrastructure.error.GenericError;
import com.fleetmanagement.infrastructure.exception.CoreException;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorContractBuilder errorContractBuilder;
    private static final Map<ExceptionState, HttpStatus> RESPONSE_ENTITY_STATUS_MAP = Map.of(
        ExceptionState.UNHANDLED_APPLICATION_ERROR, INTERNAL_SERVER_ERROR,
        ExceptionState.MALFORMED_REQUEST, BAD_REQUEST,
        ExceptionState.REQUEST_NOT_VERIFIED, BAD_REQUEST,
        ExceptionState.RESOURCE_NOT_FOUND, NOT_FOUND,
        ExceptionState.RESOURCE_ALREADY_EXIST, CONFLICT,
        ExceptionState.PRE_CONDITION_FAILED, PRECONDITION_FAILED,
        ExceptionState.PRE_CONDITION_REQUIRED, PRECONDITION_REQUIRED
    );

    @Override
    public ResponseEntity handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final String errorCode = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return buildErrorResponseEntity(ExceptionState.UNHANDLED_APPLICATION_ERROR, errorCode);
    }

    @ExceptionHandler(CoreException.class)
    public ResponseEntity handleCoreException(final CoreException coreException) {
        return buildErrorResponseEntityFromCoreException(coreException);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(final Exception exception) {
        return buildErrorResponseEntity(ExceptionState.UNHANDLED_APPLICATION_ERROR, GenericError.CODE);
    }

    private ResponseEntity buildErrorResponseEntityFromCoreException(final CoreException coreException) {
        return ResponseEntity
            .status(RESPONSE_ENTITY_STATUS_MAP.get(coreException.getState()))
            .body(errorContractBuilder.build(coreException));
    }

    private ResponseEntity buildErrorResponseEntity(final ExceptionState exceptionState, final String code) {
        return ResponseEntity.status(RESPONSE_ENTITY_STATUS_MAP.get(exceptionState))
            .body(errorContractBuilder.build(code));
    }
}
