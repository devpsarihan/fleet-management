package com.fleetmanagement.application.usecases.error;

import com.fleetmanagement.application.error.ApplicationError;

public final class PackageServiceError {

    public static final ApplicationError PACKAGE_NOT_FOUND = ApplicationError.builder().code("PACKAGE-ERROR-1")
        .message("Package is not found").build();
    public static final ApplicationError PACKAGE_ALREADY_EXIST = ApplicationError.builder().code("PACKAGE-ERROR-2")
        .message("Package already is existed").build();
    public static final ApplicationError PACKAGES_NOT_FOUND = ApplicationError.builder().code("PACKAGE-ERROR-3")
        .message("Packages are not found").build();

    private PackageServiceError() {
    }
}
