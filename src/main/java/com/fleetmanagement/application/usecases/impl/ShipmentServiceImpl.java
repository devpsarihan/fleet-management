package com.fleetmanagement.application.usecases.impl;

import com.fleetmanagement.api.model.request.DeliveryRequest;
import com.fleetmanagement.api.model.request.RouteRequest;
import com.fleetmanagement.domain.model.dto.*;
import com.fleetmanagement.api.model.request.ShipmentRequest;
import com.fleetmanagement.application.service.DeliveryOperationService;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.application.usecases.ShipmentService;
import com.fleetmanagement.application.usecases.VehicleService;
import com.fleetmanagement.domain.model.enumerated.DeliveryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fleetmanagement.application.util.MatchCharactersUtil.getBagBarcodes;
import static com.fleetmanagement.application.util.MatchCharactersUtil.getPackageBarcodes;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private static Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);
    private final DeliveryPointService deliveryPointService;
    private final VehicleService vehicleService;
    private final Map<DeliveryType, DeliveryOperationService> deliveryOperationServiceMap;

    public ShipmentServiceImpl(DeliveryPointService deliveryPointService,
        VehicleService vehicleService,
        List<DeliveryOperationService> deliveryOperationServices) {
        this.deliveryPointService = deliveryPointService;
        this.vehicleService = vehicleService;
        this.deliveryOperationServiceMap = deliveryOperationServices.stream()
            .collect(Collectors.toUnmodifiableMap(DeliveryOperationService::getType, Function.identity()));
    }

    @Transactional
    @Override
    public ShipmentDto distributeShipments(ShipmentRequest shipmentRequest) {
        vehicleService.getVehicleByPlate(shipmentRequest.getPlate());
        loadDeliveries(shipmentRequest.getRoute());
        ShipmentDto shipment = new ShipmentDto();
        shipment.setPlate(shipmentRequest.getPlate());
        for (RouteRequest route : shipmentRequest.getRoute()) {
            DeliveryPointDto deliveryPointDto = deliveryPointService.getDeliveryPointByCode(route.getDeliveryPoint());
            List<String> barcodes = route.getDeliveries().stream().map(DeliveryRequest::getBarcode)
                .collect(Collectors.toList());
            List<DeliveryDto> deliveries = unloadDeliveries(barcodes, deliveryPointDto.getCode());
            shipment.addRoute(prepareRouteDto(deliveryPointDto.getCode(), deliveries));
        }
        return shipment;
    }

    public void loadDeliveries(List<RouteRequest> routes) {
        routes.forEach(route -> {
            List<String> barcodes = route.getDeliveries().stream().map(DeliveryRequest::getBarcode)
                .collect(Collectors.toList());
            loadBags(barcodes);
            loadPackages(barcodes);
        });
        logger.info("All deliveries are loaded");
    }

    private void loadBags(List<String> barcodes) {
        deliveryOperationServiceMap.get(DeliveryType.BAG).loadDeliveries(getBagBarcodes(barcodes));
    }

    private void loadPackages(List<String> barcodes) {
        deliveryOperationServiceMap.get(DeliveryType.PACKAGE).loadDeliveries(getPackageBarcodes(barcodes));
    }

    private List<DeliveryDto> unloadDeliveries(List<String> barcodes, Integer deliveryPointCode) {
        List<DeliveryDto> bags = unloadBags(barcodes, deliveryPointCode);
        List<DeliveryDto> packages = unloadPackages(barcodes, deliveryPointCode);
        logger.info("All deliveries are unloaded");
        return Stream.of(bags, packages).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<DeliveryDto> unloadBags(List<String> barcodes, Integer deliveryPointCode) {
        DeliveryOperationDto deliveryOperationDto = DeliveryOperationDto.builder()
            .deliveryType(DeliveryType.BAG).deliveryPoint(deliveryPointCode).barcodes(getBagBarcodes(barcodes)).build();
        try {
            return deliveryOperationServiceMap.get(DeliveryType.BAG).unloadDeliveries(deliveryOperationDto).get();
        } catch (Exception e) {
            logger.warn("Exception is occurred when unload bags");
        }
        return Collections.emptyList();
    }

    private List<DeliveryDto> unloadPackages(List<String> barcodes, Integer deliveryPointCode) {
        DeliveryOperationDto deliveryOperationDto = DeliveryOperationDto.builder()
            .deliveryType(DeliveryType.PACKAGE).deliveryPoint(deliveryPointCode)
            .barcodes(getPackageBarcodes(barcodes)).build();
        try {
            return deliveryOperationServiceMap.get(DeliveryType.PACKAGE).unloadDeliveries(deliveryOperationDto).get();
        } catch (Exception e) {
            logger.warn("Exception is occurred when unload packages");
        }
        return Collections.emptyList();
    }

    private RouteDto prepareRouteDto(Integer deliverPoint, List<DeliveryDto> deliveries) {
        RouteDto routeDto = new RouteDto();
        routeDto.setDeliveryPoint(deliverPoint);
        routeDto.addAllDelivery(deliveries);
        return routeDto;
    }

}
