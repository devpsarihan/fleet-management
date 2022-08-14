package com.fleetmanagement.application.usecases.impl;

import com.fleetmanagement.application.usecases.BagService;
import com.fleetmanagement.domain.converter.PackageConverter;
import com.fleetmanagement.domain.model.dto.DeliveryPointDto;
import com.fleetmanagement.domain.model.dto.PackageDto;
import com.fleetmanagement.application.exception.ApplicationException;
import com.fleetmanagement.application.usecases.DeliveryPointService;
import com.fleetmanagement.application.usecases.PackageService;
import com.fleetmanagement.application.usecases.error.BagServiceError;
import com.fleetmanagement.application.usecases.error.PackageServiceError;
import com.fleetmanagement.domain.model.dto.PackageIntoBagDto;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import com.fleetmanagement.domain.verification.dto.PackageVerificationDto;
import com.fleetmanagement.infrastructure.exception.enumerated.ExceptionState;
import com.fleetmanagement.infrastructure.persistence.entity.BagEntity;
import com.fleetmanagement.infrastructure.persistence.entity.PackageEntity;
import com.fleetmanagement.infrastructure.persistence.repository.PackageRepository;
import com.fleetmanagement.infrastructure.verification.Verification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final BagService bagService;
    private final PackageRepository packageRepository;
    private final PackageConverter packageConverter;
    private final DeliveryPointService deliveryPointService;
    private final List<Verification<PackageVerificationDto>> packageVerifications;

    @Override
    @Transactional
    public Long createPackage(PackageDto packageDto) {
        boolean isPackagePresent = packageRepository.findByBarcode(packageDto.getBarcode()).isPresent();
        if (isPackagePresent) {
            throw new ApplicationException(ExceptionState.RESOURCE_ALREADY_EXIST,
                    PackageServiceError.PACKAGE_ALREADY_EXIST);
        }
        DeliveryPointDto deliveryPointDto = deliveryPointService
                .getDeliveryPointByCode(packageDto.getDeliveryPointDto().getCode());
        packageDto.setDeliveryPointDto(deliveryPointDto);
        PackageEntity packageEntity = packageRepository.save(packageConverter.from(packageDto));
        return packageEntity.getId();
    }

    @Override
    @Transactional
    public void addPackagesIntoBag(PackageIntoBagDto packageIntoBagDto) {
        Long bagId = packageIntoBagDto.getBagId();
        Long bagDeliveryPointId = bagService.getBagDeliveryPointIdById(bagId);
        List<PackageEntity> packageEntities = packageRepository.findAllByIdIn(packageIntoBagDto.getPackageIds())
                .orElseThrow(() -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND,
                        PackageServiceError.PACKAGES_NOT_FOUND));
        PackageVerificationDto packageVerificationDto = PackageVerificationDto.builder()
                .packages(packageConverter.toList(packageEntities))
                .bagDeliveryPointId(bagDeliveryPointId)
                .build();
        packageVerifications.forEach(v -> v.verify(packageVerificationDto));
        packageRepository.updatePackagesIntoBag(bagId, packageIntoBagDto.getPackageIds(), ShipmentState.LOADED_INTO_BAG);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PackageDto> getPackagesByBarcodes(List<String> barcodes) {
        List<PackageEntity> packageEntities = packageRepository.findAllByBarcodeIn(barcodes)
                .orElseThrow(() -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND,
                        PackageServiceError.PACKAGES_NOT_FOUND));
        return packageConverter.toList(packageEntities);
    }

    @Override
    @Transactional
    public void updatePackagesState(ShipmentState state, List<Long> ids) {
        packageRepository.updatePackagesState(state, ids);
    }

    @Override
    @Transactional
    public void updatePackagesStateByBagIds(ShipmentState state, List<Long> bagIds) {
        packageRepository.updatePackagesStateByBagIds(state, bagIds);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getBagIdsByPackageIds(List<Long> ids) {
        List<PackageEntity> packageEntities = packageRepository.findAllByIdIn(ids)
                .orElseThrow(() -> new ApplicationException(ExceptionState.RESOURCE_NOT_FOUND, BagServiceError.BAGS_NOT_FOUND));
        return packageEntities.stream()
                .distinct().map(PackageEntity::getBagEntity).map(BagEntity::getId).collect(Collectors.toList());
    }

}
