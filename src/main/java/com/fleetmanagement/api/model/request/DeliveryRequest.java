package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeliveryRequest implements Serializable {

    @NotEmpty
    private String barcode;
}
