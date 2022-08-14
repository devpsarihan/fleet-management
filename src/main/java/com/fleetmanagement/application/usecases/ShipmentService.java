package com.fleetmanagement.application.usecases;

import com.fleetmanagement.domain.model.dto.ShipmentDto;
import com.fleetmanagement.api.model.request.ShipmentRequest;

public interface ShipmentService {

    ShipmentDto distributeShipments(ShipmentRequest shipmentRequest);
}
