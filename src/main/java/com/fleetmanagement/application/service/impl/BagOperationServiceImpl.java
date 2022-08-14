package com.fleetmanagement.application.service.impl;

import com.fleetmanagement.application.service.error.DeliveryOperationServiceError;
import com.fleetmanagement.application.usecases.BagService;
import com.fleetmanagement.domain.model.dto.BagDto;
import com.fleetmanagement.domain.model.dto.DeliveryDto;
import com.fleetmanagement.domain.model.dto.ShipmentLogDto;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.service.DeliveryOperationService;
import com.fleetmanagement.application.usecases.ShipmenLogService;
import com.fleetmanagement.domain.model.dto.DeliveryOperationDto;
import com.fleetmanagement.domain.model.dto.ShipmentLogResultDto;
import com.fleetmanagement.domain.model.enumerated.DeliveryType;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import com.fleetmanagement.adapter.kafka.producer.KafKaProducer;
import com.fleetmanagement.adapter.kafka.model.dto.ShipmentKafkaDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class BagOperationServiceImpl implements DeliveryOperationService {

    private static Logger logger = LoggerFactory.getLogger(BagOperationServiceImpl.class);

    private final BagService bagService;
    private final ShipmenLogService shipmenLogService;
    private final KafKaProducer kafKaProducer;

    @Transactional
    @Override
    public void loadDeliveries(List<String> barcodes) {
        List<Long> bagIds = bagService.getBagsByBarcodes(barcodes).stream().map(BagDto::getId)
            .collect(Collectors.toList());
        bagService.updateBagsState(ShipmentState.LOADED, bagIds);
    }

    @Override
    @CircuitBreaker(name = "unloadDeliveries", fallbackMethod = "unloadDeliveriesCircuitBreakerFallback")
    @Bulkhead(name = "unloadDeliveries", fallbackMethod = "unloadDeliveriesBulkheadFallback", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<List<DeliveryDto>> unloadDeliveries(DeliveryOperationDto deliveryOperationDto) {
        return CompletableFuture.completedFuture(unload(deliveryOperationDto));
    }

    @Transactional
    public List<DeliveryDto> unload(DeliveryOperationDto deliveryOperationDto) {

        if (CollectionUtils.isEmpty(deliveryOperationDto.getBarcodes())) {
            return Collections.emptyList();
        }

        List<BagDto> bags = bagService.getBagsByBarcodes(deliveryOperationDto.getBarcodes());
        if (CollectionUtils.isEmpty(bags)) {
            return Collections.emptyList();
        }

        List<ShipmentLogResultDto> shipmentLogResults = logShipments(deliveryOperationDto.getDeliveryPoint(), bags);
        List<Long> bagIds = getValidShipments(shipmentLogResults);
        bagService.updateBagsState(ShipmentState.UNLOADED, bagIds);
        logger.info("Bags are unloaded");

        kafKaProducer.send(new ShipmentKafkaDto(DeliveryType.BAG, bagIds));

        return prepareDeliveries(deliveryOperationDto);
    }

    @Override
    public DeliveryType getType() {
        return DeliveryType.BAG;
    }

    private List<ShipmentLogResultDto> logShipments(Integer deliveryPointCode, List<BagDto> bags) {
        List<ShipmentLogDto> logs = new ArrayList<>();
        bags.forEach(bag -> logs.add(ShipmentLogDto.builder().shipmentId(bag.getId()).barcode(bag.getBarcode())
            .deliveryPointCode(bag.getDeliveryPointDto().getCode()).build()));
        return shipmenLogService.logShipments(deliveryPointCode, logs);
    }

    private List<Long> getValidShipments(List<ShipmentLogResultDto> shipmentLogResults) {
        return shipmentLogResults.stream().filter(ShipmentLogResultDto::isValid)
            .map(ShipmentLogResultDto::getShipmentId).collect(Collectors.toList());
    }

    private List<DeliveryDto> prepareDeliveries(DeliveryOperationDto deliveryOperationDto) {
        List<DeliveryDto> deliveries = new ArrayList<>();
        bagService.getBagsByBarcodes(deliveryOperationDto.getBarcodes()).stream().forEach(packageDto ->
            deliveries.add(DeliveryDto.builder()
                .barcode(packageDto.getBarcode())
                .state(packageDto.getState().getStateCode())
                .build())
        );
        return deliveries;
    }

    private CompletableFuture<List<DeliveryDto>> unloadDeliveriesCircuitBreakerFallback(
        DeliveryOperationDto deliveryOperationDto, Throwable ex) {
        logger.warn("Delivery type={}, circuitBreaker=open, command=unloadDeliveries, type=CircuitBreaker",
            deliveryOperationDto.getDeliveryType());
        throw new ApplicationException(ExceptionState.UNHANDLED_APPLICATION_ERROR,
            DeliveryOperationServiceError.BAG_DELIVERY_OPERATION);
    }

    private CompletableFuture<List<DeliveryDto>> unloadDeliveriesBulkheadFallback(
        DeliveryOperationDto deliveryOperationDto, Throwable ex) {
        logger.warn("Delivery type={}, circuitBreaker=open, command=unloadDeliveries, type=CircuitBreaker",
            deliveryOperationDto.getDeliveryType());
        throw new ApplicationException(ExceptionState.UNHANDLED_APPLICATION_ERROR,
            DeliveryOperationServiceError.BAG_DELIVERY_OPERATION);
    }

    private CompletableFuture<List<DeliveryDto>> unloadDeliveriesTimeLimiterFallback(
        DeliveryOperationDto deliveryOperationDto, Throwable ex) {
        logger.warn("Delivery type={}, circuitBreaker=open, command=unloadDeliveries, type=CircuitBreaker",
            deliveryOperationDto.getDeliveryType());
        throw new ApplicationException(ExceptionState.UNHANDLED_APPLICATION_ERROR,
            DeliveryOperationServiceError.BAG_DELIVERY_OPERATION);
    }

}
