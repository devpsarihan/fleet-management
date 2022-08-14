package com.fleetmanagement.domain.verification.dto;

import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.infrastructure.verification.VerificationDto;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackageVerificationDto implements VerificationDto, Serializable {

    private Long bagDeliveryPointId;
    private List<PackageDto> packages;
}
