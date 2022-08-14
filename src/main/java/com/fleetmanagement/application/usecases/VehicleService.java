package com.fleetmanagement.application.usecases;

import com.fleetmanagement.api.model.request.VehicleRequest;
import com.fleetmanagement.domain.model.dto.VehicleDto;

public interface VehicleService {

    Long createVehicle(VehicleRequest vehicleRequest);

    VehicleDto getVehicleByPlate(String plate);

}
