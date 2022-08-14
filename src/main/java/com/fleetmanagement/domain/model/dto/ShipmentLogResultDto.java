package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentLogResultDto implements Serializable {

    private Long shipmentId;
    private String barcode;
    private boolean isValid;
}
