package com.fleetmanagement.infrastructure.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fleetmanagement.infrastructure.persistence.constant.VehicleConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = VehicleConstant.VEHICLE, uniqueConstraints = {
    @UniqueConstraint(name = VehicleConstant.UNIQUE_CONSTRAINT_NAME, columnNames = {VehicleConstant.PLATE})})
public class VehicleEntity extends AbstractEntity {

    @Column(name = VehicleConstant.PLATE)
    private String plate;

}
