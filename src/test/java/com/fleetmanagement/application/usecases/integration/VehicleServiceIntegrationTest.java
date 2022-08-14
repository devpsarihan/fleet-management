package com.fleetmanagement.application.usecases.integration;

import com.fleetmanagement.api.model.request.VehicleRequest;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.VehicleService;
import com.fleetmanagement.application.usecases.error.VehicleServiceError;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("application-prod")
@ExtendWith(SpringExtension.class)
@Testcontainers
public class VehicleServiceIntegrationTest {

    @Autowired
    private VehicleService vehicleService;

    private static final String PLATE_1 = "34 TY 34";
    private static final String PLATE_2 = "34 TR 34";
    private static final String PLATE_3 = "34 T 34";

    @Test
    public void test_createVehicle_throws_exception_when_vehicle_already_exist() {
        //given
        createVehicle();

        // when
        ApplicationException applicationException = Assertions
            .catchThrowableOfType(() -> vehicleService.createVehicle(initializeVehicleRequest()),
                ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode())
            .isEqualTo(VehicleServiceError.VEHICLE_ALREADY_IS_EXISTED.getCode());
    }

    @Test
    public void test_createVehicle_return_id_when_successfully() {
        Long vehicleId = vehicleService.createVehicle(VehicleRequest.builder().plate(PLATE_2).build());
        Assert.assertNotNull(vehicleId);
    }

    @Test
    public void test_getVehicle_throws_exception_when_vehicle_not_found() {
        // when
        ApplicationException applicationException = Assertions
            .catchThrowableOfType(() -> vehicleService.getVehicleByPlate(PLATE_3),
                ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode())
            .isEqualTo(VehicleServiceError.VEHICLE_NOT_FOUND.getCode());
    }

    private void createVehicle() {
        vehicleService.createVehicle(VehicleRequest.builder().plate(PLATE_1).build());
    }

    private VehicleRequest initializeVehicleRequest() {
        return VehicleRequest.builder().plate(PLATE_1).build();
    }


}
