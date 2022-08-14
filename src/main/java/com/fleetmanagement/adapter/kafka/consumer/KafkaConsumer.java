package com.fleetmanagement.adapter.kafka.consumer;

import com.fleetmanagement.adapter.kafka.model.dto.ShipmentKafkaDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumer {

    void consume(ConsumerRecord<String, ShipmentKafkaDto> consumerRecord);
}
