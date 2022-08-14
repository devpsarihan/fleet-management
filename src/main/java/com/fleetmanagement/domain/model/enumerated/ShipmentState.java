package com.fleetmanagement.domain.model.enumerated;

import lombok.Getter;

@Getter
public enum ShipmentState {
    CREATED(1),
    LOADED_INTO_BAG(2),
    LOADED(3),
    UNLOADED(4);

    private Integer stateCode;

    ShipmentState(Integer stateCode) {
        this.stateCode = stateCode;
    }
}
