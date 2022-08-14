package com.fleetmanagement.application.service;

import com.fleetmanagement.domain.model.dto.DeliveryDto;
import com.fleetmanagement.domain.model.dto.DeliveryOperationDto;
import com.fleetmanagement.domain.model.enumerated.DeliveryType;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DeliveryOperationService {

    void loadDeliveries(List<String> barcodes);

    CompletableFuture<List<DeliveryDto>> unloadDeliveries(DeliveryOperationDto deliveryOperationDto);

    DeliveryType getType();
}
