package com.fleetmanagement.application.usecases.impl;

import com.fleetmanagement.api.model.request.VehicleRequest;
import com.fleetmanagement.application.usecases.error.VehicleServiceError;
import com.fleetmanagement.domain.model.dto.VehicleDto;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.infrastructure.persistence.entity.VehicleEntity;
import com.fleetmanagement.infrastructure.persistence.repository.VehicleRepository;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    @Override
    public Long createVehicle(VehicleRequest vehicleRequest) {
        boolean isVehiclePresent = vehicleRepository.findByPlate(vehicleRequest.getPlate()).isPresent();
        if (isVehiclePresent) {
            throw new ApplicationException(ExceptionState.RESOURCE_ALREADY_EXIST,
                VehicleServiceError.VEHICLE_ALREADY_IS_EXISTED);
        }
        VehicleEntity vehicleEntity = vehicleRepository.save(
            VehicleEntity.builder()
                .plate(vehicleRequest.getPlate())
                .build());
        return vehicleEntity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public VehicleDto getVehicleByPlate(String plate) {
        VehicleEntity vehicleEntity = vehicleRepository.findByPlate(plate).orElseThrow(
            () -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND,
                VehicleServiceError.VEHICLE_NOT_FOUND));
        return VehicleDto.builder().id(vehicleEntity.getId()).plate(vehicleEntity.getPlate()).build();
    }
}
