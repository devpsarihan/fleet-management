package com.fleetmanagement.application.usecases;

import com.fleetmanagement.api.model.request.BagRequest;
import com.fleetmanagement.domain.model.dto.BagDto;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import java.util.List;

public interface BagService {

    Long createBag(final BagRequest bagRequest);

    Long getBagDeliveryPointIdById(Long id);

    List<BagDto> getBagsByBarcodes(List<String> barcodes);

    void updateBagsState(ShipmentState state, List<Long> ids);

}
