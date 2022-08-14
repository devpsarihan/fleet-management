package com.fleetmanagement.infrastructure.persistence.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fleetmanagement.infrastructure.persistence.constant.DeliveryPointConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DeliveryPointConstant.DELIVERY_POINT, uniqueConstraints = {
    @UniqueConstraint(name = DeliveryPointConstant.UNIQUE_CONSTRAINT_NAME, columnNames = {DeliveryPointConstant.NAME})})
public class DeliveryPointEntity extends AbstractEntity implements Serializable {

    @Column(name = DeliveryPointConstant.NAME)
    private String name;

    @Column(name = DeliveryPointConstant.CODE)
    private Integer code;
}
