package com.fleetmanagement.infrastructure.persistence.entity;

import com.fleetmanagement.infrastructure.persistence.constant.BagConstant;
import com.fleetmanagement.domain.model.enumerated.ShipmentState;
import java.io.Serializable;
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
@Table(name = BagConstant.BAG, uniqueConstraints = {
    @UniqueConstraint(name = BagConstant.UNIQUE_CONSTRAINT_NAME, columnNames = {BagConstant.BARCODE})})
public class BagEntity extends AbstractEntity implements Serializable {

    @Column(name = BagConstant.BARCODE)
    private String barcode;

    @ManyToOne
    @JoinColumn(name = BagConstant.DELIVERY_POINT_ID, referencedColumnName = BagConstant.REFERENCED_COLUMN_NAME)
    private DeliveryPointEntity deliveryPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = BagConstant.STATE)
    private ShipmentState state;

}
