package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleRequest implements Serializable {

    @NotEmpty(message = "Plate can not be empty")
    private String plate;
}
