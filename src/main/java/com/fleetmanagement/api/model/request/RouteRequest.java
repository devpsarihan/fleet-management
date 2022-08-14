package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RouteRequest implements Serializable {

    @NotEmpty
    private Integer deliveryPoint;

    @NotEmpty
    private List<DeliveryRequest> deliveries;
}
