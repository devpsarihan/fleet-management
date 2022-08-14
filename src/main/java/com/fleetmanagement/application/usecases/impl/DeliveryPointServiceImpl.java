package com.fleetmanagement.application.usecases.impl;

import com.fleetmanagement.application.usecases.error.DeliveryPointServiceError;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import com.fleetmanagement.infrastructure.persistence.repository.DeliveryPointRepository;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.domain.converter.DeliveryPointConverter;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryPointServiceImpl implements DeliveryPointService {

    private final DeliveryPointRepository deliveryPointRepository;
    private final DeliveryPointConverter deliveryPointConverter;

    @Transactional
    @Override
    public Long createDeliveryPoint(DeliveryPointDto deliveryPointDto) {
        boolean isDeliveryPointPresent = deliveryPointRepository.findByCode(deliveryPointDto.getCode()).isPresent();
        if (isDeliveryPointPresent) {
            throw new ApplicationException(ExceptionState.RESOURCE_ALREADY_EXIST,
                DeliveryPointServiceError.DELIVERY_POINT_ALREADY_EXIST);
        }
        DeliveryPointEntity deliveryPointEntity = deliveryPointRepository.save(
            deliveryPointConverter.from(deliveryPointDto));
        return deliveryPointEntity.getId();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "deliveryPoint", key = "#deliverPointCode", cacheManager = "redisCacheManager", unless = "#result==null")
    @Override
    public DeliveryPointDto getDeliveryPointByCode(final Integer deliverPointCode) {
        DeliveryPointEntity deliveryPointEntity = deliveryPointRepository.findByCode(deliverPointCode)
            .orElseThrow(() -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND,
                DeliveryPointServiceError.DELIVERY_POINT_NOT_FOUND));
        return deliveryPointConverter.to(deliveryPointEntity);
    }
}
