package com.fleetmanagement.application.usecases;

import com.fleetmanagement.domain.model.dto.ShipmentLogDto;
import com.fleetmanagement.domain.model.dto.ShipmentLogResultDto;
import java.util.List;

public interface ShipmenLogService {

    List<ShipmentLogResultDto> logShipments(Integer deliveryPointCode, List<ShipmentLogDto> shipmentsLog);

}
