package com.fleetmanagement.application.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationError {

    private String code;
    private String message;
}
