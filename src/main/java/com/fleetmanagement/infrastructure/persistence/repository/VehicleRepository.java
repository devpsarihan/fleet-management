package com.fleetmanagement.infrastructure.persistence.repository;

import com.fleetmanagement.infrastructure.persistence.entity.VehicleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByPlate(final String plate);

}
