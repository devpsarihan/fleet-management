package com.fleetmanagement.application.usecases.impl;

import com.fleetmanagement.api.model.request.BagRequest;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.BagService;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.application.usecases.error.BagServiceError;
import com.fleetmanagement.domain.converter.BagConverter;
import com.fleetmanagement.domain.model.dto.BagDto;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.infrastructure.persistence.entity.BagEntity;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import com.fleetmanagement.infrastructure.persistence.repository.BagRepository;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BagServiceImpl implements BagService {

    private final BagRepository bagRepository;
    private final BagConverter bagConverter;
    private final DeliveryPointService deliveryPointService;

    @Transactional
    @Override
    public Long createBag(final BagRequest bagRequest) {
        boolean isBagPresent = bagRepository.findByBarcode(bagRequest.getBarcode()).isPresent();
        if (isBagPresent) {
            throw new ApplicationException(ExceptionState.RESOURCE_ALREADY_EXIST,
                BagServiceError.BAG_ALREADY_EXISTED);
        }
        DeliveryPointDto deliveryPointDto = deliveryPointService.getDeliveryPointByCode(bagRequest.getDeliveryPoint());
        BagEntity bagEntity = BagEntity.builder()
            .barcode(bagRequest.getBarcode())
            .deliveryPoint(DeliveryPointEntity.builder().id(deliveryPointDto.getId()).build())
            .state(ShipmentState.CREATED)
            .build();
        BagEntity savedBagEntity = bagRepository.save(bagEntity);
        return savedBagEntity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Long getBagDeliveryPointIdById(Long id) {
        return bagRepository.findById(id)
            .orElseThrow(() -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND,
                BagServiceError.BAG_NOT_FOUND))
            .getDeliveryPoint().getId();
    }

    @Transactional
    @Override
    public List<BagDto> getBagsByBarcodes(List<String> barcodes) {
        List<BagEntity> bagEntities = bagRepository.findAllByBarcodeIn(barcodes)
            .orElseThrow(() -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND,
                BagServiceError.BAGS_NOT_FOUND));
        return bagConverter.toList(bagEntities);
    }

    @Transactional
    @Override
    public void updateBagsState(ShipmentState state, List<Long> ids) {
        bagRepository.updateBagsState(state, ids);
    }

}
