package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDto implements Serializable {

    private String barcode;
    private Integer state;
}
