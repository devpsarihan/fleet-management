package com.fleetmanagement.api.controller;

import static com.fleetmanagement.api.controller.endpoint.ShipmentEndpoint.SHIPMENTS;

import com.fleetmanagement.api.model.request.ShipmentRequest;
import com.fleetmanagement.application.usecases.ShipmentService;
import com.fleetmanagement.domain.model.dto.ShipmentDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SHIPMENTS)
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ShipmentDto> distributeShipments(@Valid @RequestBody ShipmentRequest shipmentRequest) {
        return new ResponseEntity<>(shipmentService.distributeShipments(shipmentRequest), HttpStatus.CREATED);
    }

}
