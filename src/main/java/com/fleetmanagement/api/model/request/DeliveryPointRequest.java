package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryPointRequest implements Serializable {

    @NotEmpty(message = "Delivery point name can not be empty")
    private String name;

    @NotNull(message = "Delivery point code can not be null")
    private Integer code;
}
