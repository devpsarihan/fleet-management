package com.fleetmanagement.infrastructure.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fleetmanagement.infrastructure.persistence.constant.ShipmentConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ShipmentConstant.SHIPMENT_LOG)
public class ShipmentLogEntity extends AbstractEntity {

    @Column(name = ShipmentConstant.DELIVERY_POINT_CODE)
    private Integer deliveryPointCode;

    @Column(name = ShipmentConstant.BARCODE)
    private String barcode;

}
