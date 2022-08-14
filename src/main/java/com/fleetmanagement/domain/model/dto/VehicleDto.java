package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleDto implements Serializable {

    private Long id;
    private String plate;
}
