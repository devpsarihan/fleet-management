package com.fleetmanagement.adapter.kafka.model.dto;


import com.fleetmanagement.domain.model.enumerated.DeliveryType;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ShipmentKafkaDto implements Serializable {
    private DeliveryType type;
    private List<Long> ids;
}
