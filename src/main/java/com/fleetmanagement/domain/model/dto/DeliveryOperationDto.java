package com.fleetmanagement.domain.model.dto;

import com.fleetmanagement.domain.model.enumerated.DeliveryType;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryOperationDto implements Serializable {

    private DeliveryType deliveryType;
    private Integer deliveryPoint;
    private List<String> barcodes;
}
