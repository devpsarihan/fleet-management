package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShipmentRequest implements Serializable {

    @NotEmpty(message = "Plate can not be empty")
    private String plate;

    @NotEmpty(message = "Route can not be empty")
    private List<RouteRequest> route;
}
