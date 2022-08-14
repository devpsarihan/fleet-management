package com.fleetmanagement.domain.converter;

import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.infrastructure.converter.AbstractConverter;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPointConverter extends AbstractConverter<DeliveryPointDto, DeliveryPointEntity> {

    public DeliveryPointEntity from(DeliveryPointDto deliveryPointDto) {
        return DeliveryPointEntity.builder()
            .id(deliveryPointDto.getId())
            .name(deliveryPointDto.getName())
            .code(deliveryPointDto.getCode())
            .build();
    }

    public DeliveryPointDto to(DeliveryPointEntity deliveryPointEntity) {
        return DeliveryPointDto.builder()
            .id(deliveryPointEntity.getId())
            .name(deliveryPointEntity.getName())
            .code(deliveryPointEntity.getCode())
            .build();
    }
}
