package com.fleetmanagement.application.usecases.error;

import com.fleetmanagement.application.error.ApplicationError;

public final class DeliveryPointServiceError {

    public static final ApplicationError DELIVERY_POINT_NOT_FOUND = ApplicationError.builder()
        .code("DELIVERY-POINT-ERROR-1").message("Delivery point is not found").build();
    public static final ApplicationError DELIVERY_POINT_ALREADY_EXIST = ApplicationError.builder()
        .code("DELIVERY_POINT-ERROR-2").message("Delivery point already is existed").build();

    private DeliveryPointServiceError() {
    }
}
