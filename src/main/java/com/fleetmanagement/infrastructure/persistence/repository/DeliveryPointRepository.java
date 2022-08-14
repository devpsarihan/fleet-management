package com.fleetmanagement.infrastructure.persistence.repository;

import com.fleetmanagement.infrastructure.persistence.entity.DeliveryPointEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPointRepository extends JpaRepository<DeliveryPointEntity, Long> {

    Optional<DeliveryPointEntity> findByCode(Integer code);
}
