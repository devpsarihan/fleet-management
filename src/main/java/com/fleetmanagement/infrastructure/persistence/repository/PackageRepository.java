package com.fleetmanagement.infrastructure.persistence.repository;

import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import com.fleetmanagement.infrastructure.persistence.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

    Optional<PackageEntity> findByBarcode(String barcode);

    Optional<List<PackageEntity>> findAllByIdIn(List<Long> ids);

    Optional<List<PackageEntity>> findAllByBarcodeIn(List<String> barcodes);

    @Modifying(clearAutomatically = true)
    @Query("update PackageEntity p set p.bagEntity.id = :bagId, p.state = :state where p.id in (:ids)")
    void updatePackagesIntoBag(@Param("bagId") Long bagId, @Param("ids") List<Long> ids,
                               @Param("state") ShipmentState state);

    @Modifying(clearAutomatically = true)
    @Query("update PackageEntity p set p.state = :state where p.id in (:ids)")
    void updatePackagesState(@Param("state") ShipmentState state, @Param("ids") List<Long> ids);


    @Modifying(clearAutomatically = true)
    @Query("update PackageEntity p set p.state = :state where p.bagEntity.id in (:bagIds)")
    void updatePackagesStateByBagIds(@Param("state") ShipmentState state, @Param("bagIds") List<Long> bagIds);

}
