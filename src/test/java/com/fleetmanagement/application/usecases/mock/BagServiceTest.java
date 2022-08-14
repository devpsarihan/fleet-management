package com.fleetmanagement.application.usecases.mock;


import static org.mockito.ArgumentMatchers.any;

import com.fleetmanagement.api.model.request.BagRequest;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.BagService;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.application.usecases.error.BagServiceError;
import com.fleetmanagement.application.usecases.impl.BagServiceImpl;
import com.fleetmanagement.domain.converter.BagConverter;
import com.fleetmanagement.domain.model.dto.BagDto;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import com.fleetmanagement.infrastructure.persistence.entity.BagEntity;
import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import com.fleetmanagement.infrastructure.persistence.repository.BagRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BagServiceTest {

    private BagService bagService;

    @Mock
    private BagRepository bagRepository;

    @Mock
    private BagConverter bagConverter;

    @Mock
    private DeliveryPointService deliveryPointService;

    public BagServiceTest() {
        MockitoAnnotations.openMocks(this);
        bagService = new BagServiceImpl(bagRepository, bagConverter, deliveryPointService);
    }

    @Test
    public void test_createBag_throws_exception_when_bag_already_exist() {

        // given
        BagDto bag = BagDto.builder().build();
        Mockito.when(bagRepository.findByBarcode(any())).thenReturn(Optional.of(BagEntity.builder().build()));

        // when
        ApplicationException applicationException = Assertions.catchThrowableOfType(() -> bagService.createBag(initializeBagRequest()),
            ApplicationException.class);

        // then
        Assertions.assertThat(applicationException.getCode()).isEqualTo(BagServiceError.BAG_ALREADY_EXISTED.getCode());
    }

    @Test
    public void test_createBag_return_id_when_successfully() {
        // given
        ArgumentCaptor<BagEntity> bagEntityArgumentCaptor = ArgumentCaptor.forClass(BagEntity.class);

        Mockito.when(bagRepository.findByBarcode(any())).thenReturn(Optional.empty());
        Mockito.when(deliveryPointService.getDeliveryPointByCode(1))
            .thenReturn(initializeDeliveryPointDto());
        Mockito.when(bagRepository.save(bagEntityArgumentCaptor.capture()))
            .thenReturn(initializeBagEntity());

        // when
        Long bagId = bagService.createBag(initializeBagRequest());

        // then
        Assertions.assertThat(bagId).isEqualTo(1L);
    }

    private BagRequest initializeBagRequest(){
        return BagRequest.builder()
            .barcode("BAG-1")
            .deliveryPoint(1)
            .build();
    }

    private BagEntity initializeBagEntity() {
        return BagEntity.builder().id(1L).barcode("BAG-1").state(ShipmentState.CREATED)
            .deliveryPoint(DeliveryPointEntity.builder().id(1L).code(1).name("BRANCH").build()).build();
    }

    private DeliveryPointDto initializeDeliveryPointDto() {
        return DeliveryPointDto.builder().id(1L).code(1).name("BRANCH").build();
    }
}
