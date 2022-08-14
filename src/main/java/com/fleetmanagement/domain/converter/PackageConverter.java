package com.fleetmanagement.domain.converter;

import com.fleetmanagement.domain.model.dto.BagDto;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.infrastructure.converter.AbstractConverter;
import com.fleetmanagement.infrastructure.persistence.entity.BagEntity;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import com.fleetmanagement.infrastructure.persistence.entity.PackageEntity;
import org.springframework.stereotype.Component;

@Component
public class PackageConverter extends AbstractConverter<PackageDto, PackageEntity> {

    public PackageEntity from(PackageDto packageDto) {
        return PackageEntity.builder()
                .id(packageDto.getId())
                .barcode(packageDto.getBarcode())
                .deliveryPoint(DeliveryPointEntity.builder().id(packageDto.getDeliveryPointDto().getId())
                        .code(packageDto.getDeliveryPointDto().getCode()).build())
                .state(packageDto.getState())
                .volumetricWeight(packageDto.getVolumetricWeight())
                .bagEntity(
                        packageDto.getBagDto() == null ? null : BagEntity.builder().id(packageDto.getBagDto().getId()).build())
                .build();
    }

    public PackageDto to(PackageEntity packageEntity) {
        return PackageDto.builder()
                .id(packageEntity.getId())
                .barcode(packageEntity.getBarcode())
                .deliveryPointDto(DeliveryPointDto.builder()
                        .id(packageEntity.getDeliveryPoint().getId())
                        .code(packageEntity.getDeliveryPoint().getCode()).build())
                .state(packageEntity.getState())
                .volumetricWeight(packageEntity.getVolumetricWeight())
                .bagDto(packageEntity.getBagEntity() == null ? null : BagDto.builder().
                        id(packageEntity.getBagEntity().getId())
                        .barcode(packageEntity.getBagEntity().getBarcode())
                        .build())
                .build();
    }
}
