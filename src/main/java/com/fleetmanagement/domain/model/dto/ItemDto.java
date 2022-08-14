package com.fleetmanagement.domain.model.dto;

import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ItemDto implements Serializable {

    private Long id;
    private String barcode;
    private DeliveryPointDto deliveryPointDto;
    private ShipmentState state;
}
