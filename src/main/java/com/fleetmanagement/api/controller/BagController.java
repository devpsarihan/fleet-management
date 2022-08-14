package com.fleetmanagement.api.controller;

import static com.fleetmanagement.api.controller.endpoint.BagEndpoint.BAGS;

import com.fleetmanagement.api.model.response.BagResponse;
import com.fleetmanagement.application.usecases.BagService;
import com.fleetmanagement.api.model.request.BagRequest;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BAGS)
@RequiredArgsConstructor
public class BagController {

    private final BagService bagService;

    @PostMapping
    public ResponseEntity<BagResponse> createBag(@Valid @RequestBody BagRequest request) {
        Long bagId = bagService.createBag(request);
        return new ResponseEntity<>(BagResponse.builder().id(bagId).build(), HttpStatus.CREATED);
    }
}
