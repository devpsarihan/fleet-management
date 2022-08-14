package com.fleetmanagement.application.usecases.impl;

import com.fleetmanagement.domain.model.dto.ShipmentLogDto;
import com.fleetmanagement.infrastructure.persistence.repository.ShipmentLogRepository;
import com.fleetmanagement.application.usecases.ShipmenLogService;
import com.fleetmanagement.domain.converter.ShipmentLogConverter;
import com.fleetmanagement.domain.model.dto.ShipmentLogResultDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentLogServiceImpl implements ShipmenLogService {

    private static Logger logger = LoggerFactory.getLogger(ShipmentLogServiceImpl.class);

    private final ShipmentLogRepository shipmentLogRepository;
    private final ShipmentLogConverter converter;

    @Override
    @Transactional
    public List<ShipmentLogResultDto> logShipments(Integer deliveryPointCode, List<ShipmentLogDto> shipmentsLog) {
        List<ShipmentLogResultDto> shipmentLogResults = new ArrayList<>();
        shipmentsLog.forEach(shipmentLog -> {
            boolean isValid = true;
            if (!deliveryPointCode.equals(shipmentLog.getDeliveryPointCode())){
                shipmentLogRepository.save(converter.from(shipmentLog));
                isValid = false;
                logger.info("{} shipment attempt to deliver to the wrong point {}",
                    shipmentLog.getBarcode(), shipmentLog.getDeliveryPointCode());
            }
            shipmentLogResults.add(prepareShipmentLogResultDto(shipmentLog, isValid));
        });
        return shipmentLogResults;
    }

    private ShipmentLogResultDto prepareShipmentLogResultDto(ShipmentLogDto shipmentLog, boolean isValid) {
        return ShipmentLogResultDto.builder()
            .shipmentId(shipmentLog.getShipmentId())
            .barcode(shipmentLog.getBarcode())
            .isValid(isValid)
            .build();
    }
}
