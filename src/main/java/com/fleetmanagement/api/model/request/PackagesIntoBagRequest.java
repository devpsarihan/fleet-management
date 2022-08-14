package com.fleetmanagement.api.model.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackagesIntoBagRequest implements Serializable {

    @NotNull(message = "Bag id can not be null")
    private Long bagId;

    @NotEmpty(message = "Package ids can not be empty")
    private List<Long> packageIds;
}
