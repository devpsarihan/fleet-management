package com.fleetmanagement.application.service.error;

import com.fleetmanagement.application.error.ApplicationError;

public final class DeliveryOperationServiceError {

    public static final ApplicationError BAG_DELIVERY_OPERATION = ApplicationError.builder().code("CIRCUIT_BREAKER-1")
        .message("Bags are not unloaded").build();

    public static final ApplicationError PACKAGE_DELIVERY_OPERATION = ApplicationError.builder()
        .code("CIRCUIT_BREAKER-2")
        .message("Packages are not unloaded").build();

    public static final ApplicationError UNHANDLED_DELIVERY_OPERATION = ApplicationError.builder()
        .code("CIRCUIT_BREAKER-3")
        .message("Unhandled circuit breaker error").build();

    private DeliveryOperationServiceError() {
    }
}
