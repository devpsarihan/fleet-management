package com.fleetmanagement.infrastructure.persistence.repository;

import com.fleetmanagement.infrastructure.persistence.entity.ShipmentLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentLogRepository extends JpaRepository<ShipmentLogEntity, Long> {

}
