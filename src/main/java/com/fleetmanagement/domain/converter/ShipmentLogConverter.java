package com.fleetmanagement.domain.converter;

import com.fleetmanagement.domain.model.dto.ShipmentLogDto;
import com.fleetmanagement.infrastructure.converter.AbstractConverter;
import com.fleetmanagement.infrastructure.persistence.entity.ShipmentLogEntity;
import org.springframework.stereotype.Component;

@Component
public class ShipmentLogConverter extends AbstractConverter<ShipmentLogDto, ShipmentLogEntity> {

    public ShipmentLogEntity from(ShipmentLogDto source) {
        return ShipmentLogEntity.builder()
            .deliveryPointCode(source.getDeliveryPointCode())
            .barcode(source.getBarcode())
            .build();
    }

    @Override
    public ShipmentLogDto to(ShipmentLogEntity source) {
        return ShipmentLogDto.builder()
                .deliveryPointCode(source.getDeliveryPointCode())
                .barcode(source.getBarcode())
                .build();
    }
}
