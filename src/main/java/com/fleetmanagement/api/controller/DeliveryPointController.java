package com.fleetmanagement.api.controller;

import com.fleetmanagement.api.controller.endpoint.DeliveryPointEndpoint;
import com.fleetmanagement.api.model.request.DeliveryPointRequest;
import com.fleetmanagement.api.model.response.DeliveryPointResponse;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DeliveryPointEndpoint.DELIVERY_POINTS)
@RequiredArgsConstructor
public class DeliveryPointController {

    private final DeliveryPointService deliveryPointService;

    @PostMapping
    public ResponseEntity<DeliveryPointResponse> createDeliveryPoint(
        @Valid @RequestBody DeliveryPointRequest request) {
        Long id = deliveryPointService.createDeliveryPoint(
            DeliveryPointDto.builder().name(request.getName()).code(request.getCode()).build());
        return new ResponseEntity<>(DeliveryPointResponse.builder().id(id).build(), HttpStatus.CREATED);
    }

}
