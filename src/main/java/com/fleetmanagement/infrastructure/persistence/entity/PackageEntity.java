package com.fleetmanagement.infrastructure.persistence.entity;

import com.fleetmanagement.infrastructure.persistence.constant.PackageConstant;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PackageConstant.PACKAGE, uniqueConstraints = {
    @UniqueConstraint(name = PackageConstant.UNIQUE_CONSTRAINT_NAME, columnNames = {PackageConstant.BARCODE})})
public class PackageEntity extends AbstractEntity {

    @Column(name = PackageConstant.BARCODE)
    private String barcode;

    @ManyToOne
    @JoinColumn(name = PackageConstant.DELIVERY_POINT_ID, referencedColumnName = PackageConstant.REFERENCED_COLUMN_NAME)
    private DeliveryPointEntity deliveryPoint;

    @ManyToOne
    @JoinColumn(name = PackageConstant.BAG_ID, referencedColumnName = PackageConstant.REFERENCED_COLUMN_NAME)
    private BagEntity bagEntity;

    @Column(name = PackageConstant.VOLUMETRIC_WEIGHT)
    private Integer volumetricWeight;

    @Enumerated(EnumType.STRING)
    @Column(name = PackageConstant.STATE)
    private ShipmentState state;

}
