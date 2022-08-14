package com.fleetmanagement.application.usecases.error;

import com.fleetmanagement.application.error.ApplicationError;

public final class VehicleServiceError {

    public static final ApplicationError VEHICLE_NOT_FOUND = ApplicationError.builder().code("VEHICLE-1")
        .message("Vehicle is not found").build();
    public static final ApplicationError VEHICLE_ALREADY_IS_EXISTED = ApplicationError.builder().code("VEHICLE-2")
        .message("Vehicle already is existed").build();

    private VehicleServiceError() {
    }
}
