package com.fleetmanagement.application.usecases;

import com.fleetmanagement.domain.model.dto.DeliveryPointDto;

public interface DeliveryPointService {

    Long createDeliveryPoint(DeliveryPointDto deliveryPointDto);

    DeliveryPointDto getDeliveryPointByCode(Integer deliverPointCode);

}
