package com.fleetmanagement.application.usecases.mock;

import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.application.usecases.error.DeliveryPointServiceError;
import com.fleetmanagement.application.usecases.impl.DeliveryPointServiceImpl;
import com.fleetmanagement.domain.converter.DeliveryPointConverter;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import com.fleetmanagement.infrastructure.persistence.repository.DeliveryPointRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DeliveryPointServiceTest {

    private DeliveryPointService deliveryPointService;

    @Mock
    private DeliveryPointRepository deliveryPointRepository;

    @Mock
    private DeliveryPointConverter deliveryPointConverter;

    private static final Integer DELIVERY_POINT_CODE = 1;

    public DeliveryPointServiceTest(){
        MockitoAnnotations.openMocks(this);
        deliveryPointService = new DeliveryPointServiceImpl(deliveryPointRepository, deliveryPointConverter);
    }

    @Test
    public void test_createDeliveryPoint_throws_exception_when_delivery_point_already_exist(){
        // given
        Mockito.when(deliveryPointRepository.findByCode(DELIVERY_POINT_CODE)).thenReturn(Optional.of(initializeDeliverPointEntity()));

        // when
        ApplicationException applicationException = Assertions.catchThrowableOfType(() -> deliveryPointService.createDeliveryPoint(initializeDeliveryPointDto()),
            ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode()).isEqualTo(DeliveryPointServiceError.DELIVERY_POINT_ALREADY_EXIST.getCode());
    }

    @Test
    public void test_createDeliveryPoint_return_id_when_successfully(){
        // given
        ArgumentCaptor<DeliveryPointEntity> deliveryPointEntityArgumentCaptor = ArgumentCaptor.forClass(
            DeliveryPointEntity.class);
        Mockito.when(deliveryPointRepository.findByCode(DELIVERY_POINT_CODE)).thenReturn(Optional.empty());
        Mockito.when(deliveryPointRepository.save(deliveryPointEntityArgumentCaptor.capture())).thenReturn(initializeDeliverPointEntity());

        // when
        Long deliveryPointId = deliveryPointService.createDeliveryPoint(initializeDeliveryPointDto());

        // then
        Assertions.assertThat(deliveryPointId).isEqualTo(1L);
    }

    @Test
    public void test_getDeliveryPointByCode_throws_exception_when_delivery_point_not_found(){
        // given
        Mockito.when(deliveryPointRepository.findByCode(DELIVERY_POINT_CODE)).thenReturn(Optional.empty());

        // when
        ApplicationException applicationException = Assertions.catchThrowableOfType(() -> deliveryPointService.getDeliveryPointByCode(DELIVERY_POINT_CODE),
            ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode()).isEqualTo(DeliveryPointServiceError.DELIVERY_POINT_NOT_FOUND.getCode());
    }

    private DeliveryPointEntity initializeDeliverPointEntity(){
        return DeliveryPointEntity.builder().id(1L).code(1).name("BRANCH").build();
    }

    private DeliveryPointDto initializeDeliveryPointDto(){
        return DeliveryPointDto.builder().code(1).name("BRANCH").build();
    }

}
