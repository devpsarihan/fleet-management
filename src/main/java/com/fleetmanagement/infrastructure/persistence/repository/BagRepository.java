package com.fleetmanagement.infrastructure.persistence.repository;

import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import com.fleetmanagement.infrastructure.persistence.entity.BagEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BagRepository extends JpaRepository<BagEntity, Long> {

    Optional<BagEntity> findByBarcode(final String barcode);

    Optional<List<BagEntity>> findAllByBarcodeIn(final List<String> barcodes);

    @Modifying(clearAutomatically = true)
    @Query("update BagEntity b set b.state = :state where b.id in (:ids)")
    void updateBagsState(@Param("state") ShipmentState state, @Param("ids") List<Long> ids);

}
