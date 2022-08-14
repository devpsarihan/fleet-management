package com.fleetmanagement.api.controller;

import static com.fleetmanagement.api.controller.endpoint.PackageEndpoint.PACKAGES;

import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.api.model.request.PackageRequest;
import com.fleetmanagement.api.model.request.PackagesIntoBagRequest;
import com.fleetmanagement.api.model.response.PackageResponse;
import com.fleetmanagement.application.usecases.PackageService;
import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.domain.model.dto.PackageIntoBagDto;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PACKAGES)
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping
    public ResponseEntity<PackageResponse> createPackage(@Valid @RequestBody PackageRequest request) {
        Long id = packageService.createPackage(PackageDto.builder()
            .barcode(request.getBarcode())
            .deliveryPointDto(DeliveryPointDto.builder().code(request.getDeliveryPoint()).build())
            .volumetricWeight(request.getVolumetricWeight())
            .state(ShipmentState.CREATED)
            .build());
        return new ResponseEntity<>(PackageResponse.builder().id(id).build(), HttpStatus.CREATED);
    }

    @PutMapping
    public void addPackagesIntoBag(@Valid @RequestBody PackagesIntoBagRequest request) {
        packageService.addPackagesIntoBag(PackageIntoBagDto.builder()
            .bagId(request.getBagId())
            .packageIds(request.getPackageIds())
            .build());
    }

}
