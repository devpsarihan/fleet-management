package com.fleetmanagement.domain.verification;

import com.fleetmanagement.domain.exception.DomainException;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.domain.verification.dto.PackageVerificationDto;
import com.fleetmanagement.domain.verification.error.VerificationError;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.infrastructure.verification.Verification;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class PackageLoadedIntoBagVerification implements Verification<PackageVerificationDto> {

    @Override
    public void verify(PackageVerificationDto verificationRequest) {
        Long bagDeliveryPointId = verificationRequest.getBagDeliveryPointId();
        boolean isIrrelevantDeliverPointPresent = verificationRequest.getPackages().stream()
            .map(PackageDto::getDeliveryPointDto)
            .map(DeliveryPointDto::getId)
            .anyMatch(id -> !id.equals(bagDeliveryPointId));

        if (isIrrelevantDeliverPointPresent) {
            throw new DomainException(ExceptionState.REQUEST_NOT_VERIFIED,
                VerificationError.PACKAGE_HAS_IRRELEVANT_DELIVERY_POINT);
        }
    }
}
