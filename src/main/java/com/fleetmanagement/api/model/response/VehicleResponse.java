package com.fleetmanagement.api.model.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponse implements Serializable {

    private Long id;
}
