package com.fleetmanagement.domain.converter;

import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.domain.model.dto.BagDto;
import com.fleetmanagement.infrastructure.converter.AbstractConverter;
import com.fleetmanagement.infrastructure.persistence.entity.BagEntity;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import org.springframework.stereotype.Component;

@Component
public class BagConverter extends AbstractConverter<BagDto, BagEntity> {

    @Override
    public BagEntity from(BagDto source) {
        return BagEntity.builder()
                .id(source.getId())
                .barcode(source.getBarcode())
                .deliveryPoint(
                        DeliveryPointEntity.builder().id(source.getDeliveryPointDto().getId())
                                .code(source.getDeliveryPointDto().getCode()).build())
                .state(source.getState())
                .build();
    }

    public BagDto to(BagEntity source) {
        return BagDto.builder()
                .id(source.getId())
                .barcode(source.getBarcode())
                .deliveryPointDto(
                        DeliveryPointDto.builder().id(source.getDeliveryPoint().getId()).code(source.getDeliveryPoint()
                                .getCode()).build())
                .state(source.getState())
                .build();
    }

}
