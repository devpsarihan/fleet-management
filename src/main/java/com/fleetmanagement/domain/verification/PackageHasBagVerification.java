package com.fleetmanagement.domain.verification;

import com.fleetmanagement.domain.exception.DomainException;
import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.domain.verification.dto.PackageVerificationDto;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.domain.verification.error.VerificationError;
import com.fleetmanagement.infrastructure.verification.Verification;
import java.util.Objects;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class PackageHasBagVerification implements Verification<PackageVerificationDto> {

    @Override
    public void verify(PackageVerificationDto verificationRequest) {
        boolean isBagPresent = verificationRequest.getPackages().stream()
            .map(PackageDto::getBagDto)
            .anyMatch(Objects::nonNull);
        if (isBagPresent) {
            throw new DomainException(ExceptionState.REQUEST_NOT_VERIFIED,
                VerificationError.PACKAGE_ALREADY_IS_INTO_BAG);
        }
    }
}
