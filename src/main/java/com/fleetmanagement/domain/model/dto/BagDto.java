package com.fleetmanagement.domain.model.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class BagDto extends ItemDto implements Serializable {

}
