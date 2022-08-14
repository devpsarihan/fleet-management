package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackageIntoBagDto implements Serializable {

    private Long bagId;
    private List<Long> packageIds;
}
