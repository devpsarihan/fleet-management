package com.fleetmanagement.api.controller;

import static com.fleetmanagement.api.controller.endpoint.VehicleEndpoint.VEHICLES;

import com.fleetmanagement.api.model.request.VehicleRequest;
import com.fleetmanagement.api.model.response.VehicleResponse;
import com.fleetmanagement.application.usecases.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VEHICLES)
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(VehicleRequest vehicleRequest) {
        Long vehicleId = vehicleService.createVehicle(vehicleRequest);
        return new ResponseEntity<>(VehicleResponse.builder().id(vehicleId).build(), HttpStatus.CREATED);
    }
}
