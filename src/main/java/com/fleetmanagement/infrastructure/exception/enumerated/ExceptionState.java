package com.fleetmanagement.infrastructure.exception.enumerated;

public enum ExceptionState {
    UNHANDLED_APPLICATION_ERROR,    //500
    MALFORMED_REQUEST,              //400
    REQUEST_NOT_VERIFIED,           //400
    RESOURCE_NOT_FOUND,             //404
    RESOURCE_ALREADY_EXIST,         //409
    PRE_CONDITION_REQUIRED,         //428
    PRE_CONDITION_FAILED,           //412
}
