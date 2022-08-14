package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BagRequest implements Serializable {

    @NotEmpty(message = "Barcode can not be empty")
    private String barcode;

    @NotNull(message = "DeliveryPoint can not be null")
    private Integer deliveryPoint;
}
