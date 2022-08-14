package com.fleetmanagement.adapter.kafka.producer;

import com.fleetmanagement.adapter.kafka.model.dto.ShipmentKafkaDto;

public interface KafKaProducer {
    void send(ShipmentKafkaDto shipmentKafkaDto);
}
