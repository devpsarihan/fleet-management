package com.fleetmanagement.application.usecases.error;

import com.fleetmanagement.application.error.ApplicationError;

public final class BagServiceError {

    public static final ApplicationError BAG_NOT_FOUND = ApplicationError.builder().code("BAG-ERROR-1")
        .message("Bag is not found").build();
    public static final ApplicationError BAG_ALREADY_EXISTED = ApplicationError.builder().code("BAG-ERROR-2")
        .message("Bag already is existed").build();
    public static final ApplicationError BAGS_NOT_FOUND = ApplicationError.builder().code("BAG-ERROR-3")
        .message("Bags are not found").build();

    private BagServiceError() {
    }
}
