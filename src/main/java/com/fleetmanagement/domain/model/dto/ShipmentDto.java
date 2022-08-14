package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShipmentDto implements Serializable {

    private String plate;
    private List<RouteDto> routes = new ArrayList<>();

    public void addRoute(RouteDto route) {
        this.getRoutes().add(route);
    }

}
