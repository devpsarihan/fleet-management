package com.fleetmanagement.infrastructure.verification;

public interface Verification<T extends VerificationDto> {

    void verify(T verificationDto);
}
