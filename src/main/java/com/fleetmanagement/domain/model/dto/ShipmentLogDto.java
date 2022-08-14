package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentLogDto implements Serializable {

    private Long shipmentId;
    private Integer deliveryPointCode;
    private String barcode;
}
