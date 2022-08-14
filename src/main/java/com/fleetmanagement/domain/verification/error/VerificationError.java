package com.fleetmanagement.domain.verification.error;

import com.fleetmanagement.domain.error.DomainError;

public final class VerificationError {

    public static final DomainError PACKAGE_HAS_IRRELEVANT_DELIVERY_POINT = DomainError.builder().code("DOMAIN-1")
        .message("Package has irrelevant delivery point with the bag").build();
    public static final DomainError PACKAGE_ALREADY_IS_INTO_BAG = DomainError.builder().code("DOMAIN-2")
        .message("Package already is into the bag").build();

    private VerificationError() {
    }
}
