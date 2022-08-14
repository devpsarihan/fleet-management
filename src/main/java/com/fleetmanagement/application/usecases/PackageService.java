package com.fleetmanagement.application.usecases;

import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import com.fleetmanagement.domain.model.dto.PackageIntoBagDto;

import java.util.List;

public interface PackageService {

    Long createPackage(PackageDto packageDto);

    void addPackagesIntoBag(PackageIntoBagDto addPackageToBagDto);

    List<PackageDto> getPackagesByBarcodes(List<String> barcodes);

    void updatePackagesState(ShipmentState state, List<Long> ids);

    void updatePackagesStateByBagIds(ShipmentState state, List<Long> bagIds);

    List<Long> getBagIdsByPackageIds(List<Long> ids);

}
