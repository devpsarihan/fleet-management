package com.fleetmanagement.domain.error;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainError implements Serializable {

    private String code;
    private String message;
}
