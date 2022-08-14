package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RouteDto implements Serializable {

    private Integer deliveryPoint;
    private List<DeliveryDto> deliveries = new ArrayList<>();

    public void addAllDelivery(final List<DeliveryDto> barcodes) {
        this.getDeliveries().addAll(barcodes);
    }
}
