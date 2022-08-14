package com.fleetmanagement.application.usecases.mock;

import static org.mockito.ArgumentMatchers.any;

import com.fleetmanagement.api.model.request.VehicleRequest;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.VehicleService;
import com.fleetmanagement.application.usecases.error.VehicleServiceError;
import com.fleetmanagement.application.usecases.impl.VehicleServiceImpl;
import com.fleetmanagement.domain.model.dto.VehicleDto;
import com.fleetmanagement.infrastructure.persistence.entity.VehicleEntity;
import com.fleetmanagement.infrastructure.persistence.repository.VehicleRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VehicleServiceTest {

    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    private static final String PLATE = "34 TY 34";

    public VehicleServiceTest(){
        MockitoAnnotations.openMocks(this);
        vehicleService = new VehicleServiceImpl(vehicleRepository);
    }

    @Test
    public void test_createVehicle_throws_exception_when_vehicle_already_exist(){
        // given
        VehicleRequest vehicle = VehicleRequest.builder().build();
        Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(Optional.of(VehicleEntity.builder().build()));

        // when
        ApplicationException applicationException = Assertions.catchThrowableOfType(() -> vehicleService.createVehicle(vehicle),
            ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode()).isEqualTo(VehicleServiceError.VEHICLE_ALREADY_IS_EXISTED.getCode());
    }

    @Test
    public void test_createVehicle_return_id_when_successfully() {
        // given
        ArgumentCaptor<VehicleEntity> vehicleEntityArgumentCaptor = ArgumentCaptor.forClass(VehicleEntity.class);
        Mockito.when(vehicleRepository.findByPlate(PLATE)).thenReturn(Optional.empty());
        Mockito.when(vehicleRepository.save(vehicleEntityArgumentCaptor.capture())).thenReturn(initializeVehicleEntity());

        // when
        Long vehicleId = vehicleService.createVehicle(initializeVehicleRequest());

        // then
        Assertions.assertThat(vehicleEntityArgumentCaptor.getValue().getPlate()).isEqualTo(PLATE);
        Assertions.assertThat(vehicleId).isEqualTo(1L);
    }

    @Test
    public void test_getVehicleByPlate_throws_exception_when_vehicle_not_found(){
        // given
        Mockito.when(vehicleRepository.findByPlate(any())).thenReturn(Optional.empty());

        // when
        ApplicationException applicationException = Assertions.catchThrowableOfType(() -> vehicleService.getVehicleByPlate(PLATE),
            ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode()).isEqualTo(VehicleServiceError.VEHICLE_NOT_FOUND.getCode());
    }

    @Test
    public void test_getVehicleByPlate_return_vehicle_dto_when_successfully() {
        // given
        Mockito.when(vehicleRepository.findByPlate(PLATE)).thenReturn(Optional.of(initializeVehicleEntity()));

        // when
        VehicleDto vehicle = vehicleService.getVehicleByPlate(PLATE);

        // then
        Assertions.assertThat(vehicle.getId()).isEqualTo(1L);
        Assertions.assertThat(vehicle.getPlate()).isEqualTo(PLATE);
    }

    private VehicleRequest initializeVehicleRequest(){
        return VehicleRequest.builder().plate(PLATE).build();
    }

    private VehicleEntity initializeVehicleEntity(){
        return VehicleEntity.builder().id(1L).plate(PLATE).build();
    }

}
